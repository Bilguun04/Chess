package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.*;


// Authors : Samy Harras & Zyad El Fettahi & Ayham Nassar 

public class ShipmentOrderController {
	private static WareFlow wareFlow = WareFlowApplication.getWareFlow();
	public static boolean toBeApproved;
	// containerNumber -1 means that no container is specified and quantity has to be -1 as well
	
	// Author: Ziyad El Fettahi
	public static String addShipmentOrder(int id, Date placedOnDate, String description,
			String username, int containerNumber, int quantity) {
   
		int i = 0;
		int numOrders = wareFlow.numberOfOrders();
		ItemContainer container = null;
    
		//checking if id already exists
		while(i < numOrders) {
			if(wareFlow.getOrder(i).getId() == id ) {
				return "Order id already exists" ;
				}
			i++;
		}
    
		// checking if OrderPlacer exists
		User user = null;
		boolean gotUser = false;
    
		// checking if OrderPlacer is a client
		for(Client client : wareFlow.getClients()) {
			if(username.equals(client.getUsername())){
				user = client;
				gotUser = true;
				break;
			} 
		}
    
		// checking if OrderPlacer is an employee
		for(Employee employee : wareFlow.getEmployees()){
			if(username.equals(employee.getUsername())) {
				user = employee;
				gotUser = true;
				break;
			}
		}
    
		// checking if OrderPlacer is not a manager
		if(!(gotUser) && !(username.equals(wareFlow.getManager().getUsername()))){
			return "The order placer does not exist";
		}
    
		// OrderPlacer is the manager
		if( username.equals(wareFlow.getManager().getUsername())) {
			user = wareFlow.getManager();
		}
    
		// description cannot be empty
		if (description == "") {
			return "Order description cannot be empty" ;
		}
    
		// checking if container exists
		int j = 0;
		while(j < wareFlow.numberOfItemContainers() && containerNumber != -1) {
			if(wareFlow.getItemContainer(j).getContainerNumber() == containerNumber){
				container = wareFlow.getItemContainer(j);
				break;
			}
			j++;
			if(j == wareFlow.numberOfItemContainers()) {
				return "The container does not exist";
			}
		}
     
		// Order quantity must be larger than 0 when container is specified
		if (containerNumber != -1 && !(quantity > 0)) {
			return "Order quantity must be larger than 0 when container is specified";
		}
    
		// Order quantity must 0 when container is not specified
		if(containerNumber == -1 && quantity != 0){
			return "Order quantity must 0 when container is not specified";
		}
    
		//adding order
		if(containerNumber == -1) {
			wareFlow.addOrder(id, placedOnDate, description, quantity, user);
			return "Order successfully added" ;
		}
		
		if(containerNumber != -1) {
			wareFlow.addOrder(id, placedOnDate, description, quantity, user).setContainer(container);
			return "Order successfully added" ;
		}
		return "Order failed to add";
	}
	
	// Author: Ziyad El Fettahi
	public static String updateShipmentOrder(int id, Date newPlacedOnDate, String newDescription,
			String newUsername, int newContainerNumber, int newQuantity) {
    
		int i = 0;
		User user = null;
		ItemContainer container = null;
		int numOrders = wareFlow.numberOfOrders(); 
		
		// Check for the existence of the order
		while(i < numOrders) {
			if(wareFlow.getOrder(i).getId() == id ) {
				break;
			}
			if(i == numOrders) {
				return "Order id doesn't exist";
			}
			i++;
		}
    
		// Check validity of order description
		if (newDescription == "") {
			return "Order description cannot be empty" ;
		}
    
		// Check the existence of the container
		int j = 0;
		
		while(j < wareFlow.numberOfItemContainers() && newContainerNumber != -1) {
			if(wareFlow.getItemContainer(j).getContainerNumber() == newContainerNumber){
				container = wareFlow.getItemContainer(j);
				break;
			}
			j++;
			if(j == wareFlow.numberOfItemContainers()) {
				return "The container does not exist";
			}
		}
    
    
		// Check the validity of order quantity
		if (newContainerNumber != -1 && newQuantity <= 0) {
			return "Order quantity must be larger than 0 when container is specified";
		}
   
		if(newContainerNumber == -1 && newQuantity != 0){
			return "Order quantity must 0 when container is not specified";
		}
    
		// Check for the existence of the order placer
		boolean gotUser = false;
		for(Client client : wareFlow.getClients()) {
			if(newUsername.equals(client.getUsername())) {
				user = client;
				gotUser = true;
				break;
			}
		}
		
		for(Employee employee : wareFlow.getEmployees()){
			if(newUsername.equals(employee.getUsername())) {
				user = employee;
				gotUser = true;
				break;
			}
		}
		
		if(!(gotUser) && !(newUsername.equals(wareFlow.getManager().getUsername()))){
			return "The order placer does not exist";
		}

		if( newUsername.equals(wareFlow.getManager().getUsername())) {
			user = wareFlow.getManager();
		}
  
  
		// setting container
		if(newContainerNumber != -1) {
			wareFlow.getOrder(i).setContainer(container);
			}
		
		if(newContainerNumber == -1) {
			wareFlow.getOrder(i).setContainer(null);
		}
    
    
		//updating order
		wareFlow.getOrder(i).setOrderPlacer(user);
		wareFlow.getOrder(i).setPlacedOnDate(newPlacedOnDate);
		wareFlow.getOrder(i).setDescription(newDescription);
		wareFlow.getOrder(i).setQuantity(newQuantity);
		return "Order updated successfully";
	}
  	
	// Author : Samy Harras
	public static void deleteShipmentOrder(int id) {
	 
		// Retrieve the current list of all orders from the WareFlow application instance.
		List<ShipmentOrder> orders = WareFlowApplication.getWareFlow().getOrders();
	  
		// Iterate over the list of orders.
		for (ShipmentOrder order : orders) {
			
			// Check if the current order's ID matches the ID we want to delete.
			if (order.getId() == id) {
				// If a match is found, call the delete method on the matching order.
				order.delete();
				// Exit the loop early since we've found and deleted the matching order.
				break;
			}
		}
	}

	// Author: Ayham Nassar
	public static List<TOShipmentOrder> getOrders() {
		
		// Fetch the current list of shipment orders from the application's model
		List<ShipmentOrder> shipmentOrders = WareFlowApplication.getWareFlow().getOrders();
		List<TOShipmentOrder> toShipmentOrders = new ArrayList<>();
		
		// Convert each ShipmentOrder object to a TOShipmentOrder object
		for (ShipmentOrder shipmentOrder : shipmentOrders) {
			TOShipmentOrder toShipmentOrder = convertToTOShipmentOrder(shipmentOrder);
			toShipmentOrders.add(toShipmentOrder);
		}
		return toShipmentOrders;
		}
	
	//Author: Samy Harras
	public static TOShipmentOrder convertToTOShipmentOrder(ShipmentOrder shipmentOrder) {
	    if (shipmentOrder == null) return null;
	    
	    // Assuming itemName should be null when no container is associated, otherwise, fetch the item name.
	    String itemName = shipmentOrder.hasContainer() ? shipmentOrder.getContainer().getItemType().getName() : null;
	    // Assuming -1 for expectedLifeSpanInDays when no container is associated.
	    int expectedLifeSpanInDays = shipmentOrder.hasContainer() ? shipmentOrder.getContainer().getItemType().getExpectedLifeSpanInDays() : -1;
	    Date addedOnDate = shipmentOrder.hasContainer() ? shipmentOrder.getContainer().getAddedOnDate() : null;
	    int areaNumber = shipmentOrder.hasContainer() ? shipmentOrder.getContainer().getAreaNumber() : -1;
	    int slotNumber = shipmentOrder.hasContainer() ? shipmentOrder.getContainer().getSlotNumber() : -1;

	    List<TOShipmentNote> toShipmentNotes = new ArrayList<>();
	    for (ShipmentNote note : shipmentOrder.getShipmentNotes()) {
	        toShipmentNotes.add(new TOShipmentNote(note.getDate(), note.getDescription(), note.getNoteTaker().getUsername()));
	    }

	    String status = shipmentOrder.getStatus() != null ? shipmentOrder.getStatus().toString() : null;
	    String orderPickerUsername = shipmentOrder.getOrderPicker() != null ? shipmentOrder.getOrderPicker().getUsername() : null;
	    String timeToFullfillString = shipmentOrder.getTimeToFullfill() != null ? shipmentOrder.getTimeToFullfill().toString() : null;
	    String priorityString = shipmentOrder.getPriority() != null ? shipmentOrder.getPriority().toString() : null;
	    boolean toBeApproved = shipmentOrder.toBeApproved();

	    return new TOShipmentOrder(
	        shipmentOrder.getId(),
	        shipmentOrder.getQuantity(),
	        shipmentOrder.getPlacedOnDate(),
	        shipmentOrder.getDescription(),
	        shipmentOrder.getOrderPlacer().getUsername(),
	        status,
	        orderPickerUsername,
	        timeToFullfillString,
	        priorityString,
	        toBeApproved,
	        itemName,
	        expectedLifeSpanInDays,
	        addedOnDate,
	        areaNumber,
	        slotNumber,
	        toShipmentNotes.toArray(new TOShipmentNote[0])
	    );
	}

	// Author : Samy Harras
	private static String checkValidity(int orderId) {
		
		//check if ticketId is valid
		List<ShipmentOrder> orders = wareFlow.getOrders();
		boolean exists = false;
		for (ShipmentOrder order : orders) {
			if (order.getId() == (orderId)) {
				exists = true;
			}
		}
		if (!exists) {
			return "shipment order does not exist.";
		}
		//WareFlowPersistence.save();
		return "";
	}  

	// Author : Samy Harras
	public static String assignShipmentOrder(int orderId, WarehouseStaff staff, ShipmentOrder.TimeEstimate timeToComplete, ShipmentOrder.PriorityLevel priority, Boolean toBeApproved){

		//Fetch the corresponding ticket from the system
		List<ShipmentOrder> orders = wareFlow.getOrders();
		ShipmentOrder order = null;
		for (ShipmentOrder o: orders){
			if(o.getId()==orderId) order = o;
		}
		if (order==null) return "shipment order does not exist.";
		//Fetch the corresponding HotelStaff that will be assigned to the ticket

		if (staff==null) return "Staff to assign does not exist.";

		//Assign ticket (changes the status of the ticket) if state was Open
		Boolean isAssigned = false;

		if (order.getStatus()!=null) {
			isAssigned = order.assignShipmentOrder(wareFlow.getManager(), timeToComplete, priority, toBeApproved);
			
			//Error messages if the ticket has the wrong state
			if(!isAssigned){
				if (order.getStatus().equals(ShipmentOrder.Status.Assigned)) {
					return "The shipment order is already assigned.";
		        } else if (order.getStatus().equals(ShipmentOrder.Status.Completed)) {
		        	return "Cannot assign a shipment order which is completed.";
		        } else if (order.getStatus().equals(ShipmentOrder.Status.Closed)) {
		        	return "Cannot assign a shipment order which is closed.";
		        } else if (order.getStatus().equals(ShipmentOrder.Status.InProgress)) {
		        	return "Cannot assign a shipment order which is in progress.";
		        }
			}
		}

		//Add the ticket to the list of tasks of a staff
		staff.addShipmentOrder(order);
		//Set additional variables to the ticket
		order.setOrderPicker(staff);
		order.setTimeToFullfill(timeToComplete);
		order.setPriority(priority);
		if(toBeApproved) {
			order.setOrderApprover(wareFlow.getManager());
		}
		/*try {
			WareFlowPersistence.save();
		} catch (RuntimeException e) {
			return e.getMessage();
		}*/
		return "";
	}

	// Author : Samy Harras
	public static String startShipmentOrder(int orderId){
		//Fetch the corresponding ticket from the system
		List<ShipmentOrder> orders = wareFlow.getOrders();
		ShipmentOrder order = null;
		for (ShipmentOrder o: orders){
			if(o.getId()==orderId) order = o;
		}

		if (order==null) return "shipment order does not exist.";

		//start work on maintenance ticket
		boolean isStarted = order.startShipmentOrder();

		//Error messages if the ticket has the wrong state
		if(!isStarted){
			if (order.getStatus().equals(ShipmentOrder.Status.Open)) {
				return "Cannot start a shipment order which is open.";
			} else if (order.getStatus().equals(ShipmentOrder.Status.Completed)) {
				return "Cannot start a shipment order which is completed.";
			} else if (order.getStatus().equals(ShipmentOrder.Status.Closed)) {
				return "Cannot start a shipment order which is closed.";
			} else if (order.getStatus().equals(ShipmentOrder.Status.InProgress)) {
				return "The shipment order is already in progress.";
			}
		}
		
		/*try {
			WareFlowPersistence.save();
		} catch (RuntimeException e) {
			return e.getMessage();
		}*/
		return "";
	}

	// Author : Samy Harras
	public static String completeShipmentOrder (int orderId) {

		if (checkValidity(orderId) == "") {

			//get ticket with its ID
			List<ShipmentOrder> orders = wareFlow.getOrders();
			ShipmentOrder order = null;
			for (ShipmentOrder o : orders) {
				if (o.getId() == orderId) {
					order = o;
					break;
				}
			}

			//checking states for errors
			if (order.getStatus().equals(ShipmentOrder.Status.Open)){
				return "Cannot complete a shipment order which is open.";

			} else if (order.getStatus().equals(ShipmentOrder.Status.Assigned)) {
				return "Cannot complete a shipment order which is assigned.";

		    } else if (order.getStatus().equals(ShipmentOrder.Status.Closed)) {
		    	return "The shipment order is already closed.";

		    } else if (order.getStatus().equals(ShipmentOrder.Status.Completed)) {
		    	return "The shipment order is already completed.";
		    }

		    //checking if in correct state
			if (order.getStatus().equals(ShipmentOrder.Status.InProgress)){
				if (order.toBeApproved()) {

		        // setState to Resolved if requiresApproval
		        order.completeShipmentOrder();

				} else {
					// setState to Closed if doesn't requiresApproval
					order.completeShipmentOrder();
				}
			}  } else {
				return checkValidity(orderId);
			}
		/*try {
			WareFlowPersistence.save();
		} catch (RuntimeException e) {
			return e.getMessage();
		}*/
		return "";
	}

	// Author : Samy Harras
	public static String approveShipmentOrder(int orderId){

		if (checkValidity(orderId).equals("")) {
		// Getting the ticket with its ID
			List<ShipmentOrder> orders = wareFlow.getOrders();
			ShipmentOrder order = null;
			for (ShipmentOrder o : orders) {
				if (o.getId() == orderId) {
					order = o;
		            break;
		        }
			}

			// Checking the states for errors
			if (order.getStatus().equals(ShipmentOrder.Status.Open)){
				return "Cannot approve a shipment order which is open.";
				
			} else if (order.getStatus().equals(ShipmentOrder.Status.Assigned)) {
				return "Cannot approve a shipment order which is assigned.";

			} else if (order.getStatus().equals(ShipmentOrder.Status.Closed)) {
				return "The shipment order is already closed.";

			} else if (order.getStatus().equals(ShipmentOrder.Status.InProgress)) {
				return "Cannot approve a shipment order which is in progress.";
			}

			if (order.getStatus().equals(ShipmentOrder.Status.Completed)) {

				// setState to Closed if current-state is Resolved
		        Manager manager = wareFlow.getManager();
		        order.approveShipmentOrder();
		    }
		} else {
			return checkValidity(orderId);
		}
		
		/*try {
			WareFlowPersistence.save();
		} catch (RuntimeException e) {
			return e.getMessage();
		}*/
		return "";
	}
	  
	// Author : Samy Harras
	public static String disapproveShipmentOrder(int orderId, Date date, String reason) {

		if (checkValidity(orderId) == "") {

			//getting ticket with its id
			List<ShipmentOrder> orders = wareFlow.getOrders();
			ShipmentOrder order = null;
			for (ShipmentOrder o : orders) {
				if (o.getId() == orderId) {
					order = o;
					break;
				}
			}


			//checking state for errors
			if (order.getStatus().equals(ShipmentOrder.Status.Open)){
				return "Cannot disapprove a shipment order which is open.";

			} else if (order.getStatus().equals(ShipmentOrder.Status.Assigned)) {
				return "Cannot disapprove a shipment order which is assigned.";

		    } else if (order.getStatus().equals(ShipmentOrder.Status.Closed)) {
		    	return "Cannot disapprove a shipment order which is closed.";

		    } else if (order.getStatus().equals(ShipmentOrder.Status.InProgress)) {
		    	return "Cannot disapprove a shipment order which is in progress.";

		    }

		    order.disapproveShipmentOrder(date, reason);

		    //setting Description
		    order.addShipmentNote(date, reason, wareFlow.getManager());

		    //setting RaisedOnDate
		    order.setPlacedOnDate(date);
		} else {
			return checkValidity(orderId);
		}
		/*try {
			WareFlowPersistence.save();
		} catch (RuntimeException e) {
			return e.getMessage();
		}*/
		return "";
	}

	// Author : Samy Harras
	public static List<String> getPriorityValues(){
		List<String> values = new ArrayList<>();
		for(ShipmentOrder.PriorityLevel p: ShipmentOrder.PriorityLevel.values()){
			values.add(p.toString());
		}
		return values;
	}

	
	// Author : Samy Harras
	public static ShipmentOrder.PriorityLevel toPriorityValue(String priority){
		if(priority != null) return ShipmentOrder.PriorityLevel.valueOf(priority);
		else return null;
	}
	
	// Author : Samy Harras

	public static List<String> getTimeEstimateValues(){
		List<String> values = new ArrayList<>();
		for(ShipmentOrder.TimeEstimate p: ShipmentOrder.TimeEstimate.values()){
			values.add(p.toString());
		}
		return values;
	}

	
	// Author : Samy Harras
	public static ShipmentOrder.TimeEstimate toTimeEstimateValue(String timeEstimate){
		if(timeEstimate != null) return ShipmentOrder.TimeEstimate.valueOf(timeEstimate);
		else return null;
	}
	
}
