package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.ShipmentNote;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder;
import ca.mcgill.ecse.wareflow.model.User;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.model.WarehouseStaff;

//Author : Bilguun Tegshbayar (all methods) and Samy Harras (helped fix add and update methods)
public class ShipmentNoteController {
	
	private static WareFlow wareflow = WareFlowApplication.getWareFlow();
	
	// Helper method to find an order by ID
	private static ShipmentOrder getOrderById(int orderId) {
		for (ShipmentOrder order : wareflow.getOrders()) {
			if (order.getId() == orderId) {
				return order;
			}
		}
		return null; 
	}

  
	// Helper method to find a note by index
	private static ShipmentNote getnoteByIndex(int orderId, int index) {
		
		// Check that the order is not null
		ShipmentOrder order = getOrderById(orderId);
		if (order == null) {
			return null;
		}
    
		// Return the note based on its index
		List<ShipmentNote> notes = order.getShipmentNotes();
		if (index < 0 || index >= notes.size()) {
			return null;
		}
		return notes.get(index);
	}
  
	
	public static String addShipmentNote(Date date, String description, int orderID, String username) {
		
		// Check if the orderID exists in the system
		ShipmentOrder order = getOrderById(orderID);
		if (order == null) {
			return "Order does not exist";
		}

	    // Check if the notetaker with username exists in the system
	    User notetaker = WarehouseStaff.getWithUsername(username);
	    if (notetaker == null) {
	        return "Staff does not exist";
	    }

	    // Check if the description is valid
	    if (description == null || description.trim().isEmpty()) {
	        return "Order description cannot be empty";
	    }

	    // Create and add the shipment note
	    ShipmentNote note = new ShipmentNote(date, description, order, (WarehouseStaff) notetaker);
	    order.addShipmentNote(note);
	    return "Successfully added shipment note";
	}


  
	// index starts at 0
	public static String updateShipmentNote(int orderID, int index, Date newDate,
			String newDescription, String newUsername) {
    
		// Check if the order exists in the system.
		ShipmentOrder order = null;
		for (ShipmentOrder o : wareflow.getOrders()){
			if (o.getId() == orderID){
				order = o;
				break;
			}
		}
    
		// If the order does not exist, return an error message.
		if (order == null){
			return "Order does not exist";
		}
    
		// Check if the note exists in the system.
		List<ShipmentNote> notes = order.getShipmentNotes();
		if (index < 0 || index >= notes.size()){
			return "Note does not exist";
		}
    
		// Check if the notetaker with username exists in the system.
		ShipmentNote note = notes.get(index);
		User notetaker = WarehouseStaff.getWithUsername(newUsername);
		if (notetaker == null){
			return "Staff does not exist";
		}

		//Check if the new description is valid.
		if (newDescription == null || newDescription.equals("")){
			return "Order description cannot be empty";
		}

		note.setDate(newDate);
		note.setDescription(newDescription);
		note.setNoteTaker((WarehouseStaff) notetaker);
		return "Successfully updated shipment note";
	}

  
	// index starts at 0
	public static void deleteShipmentNote(int orderID, int index) {
		
		// Check if the order exists in the system.
		boolean order_existence = false;
		for (ShipmentOrder order : wareflow.getOrders()){
			if (order.getId() == orderID){
				order_existence = true;
				break;
			}
		}
		if (!order_existence){
			return;
		}

		// Get note to delete.
		ShipmentNote notetodelete = getnoteByIndex(orderID, index);

		// Check if the note to delete exists in the system, if so delete it.
		if (notetodelete == null){
			return;
		} 
		else {
			notetodelete.delete();
			return;
		}
	}
}
