package ca.mcgill.ecse.wareflow.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Order;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayNameGenerator.Simple;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.controller.ShipmentNoteController;
import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;
import ca.mcgill.ecse.wareflow.controller.TOShipmentNote;
import ca.mcgill.ecse.wareflow.controller.TOShipmentOrder;
import ca.mcgill.ecse.wareflow.model.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Date;

public class ShipmentOrderStepDefinitions {
	
	private static WareFlow wareflow = WareFlowApplication.getWareFlow();
	private String error;
	private TOShipmentOrder toorder;
	private TOShipmentNote tonote;
	private ShipmentOrder order;
	private List<TOShipmentOrder> TOOrders;
	private List<ShipmentOrder> orders;


  /**
   * Gherkin step definition to add new employees to the WareFlow application.
   * @param dataTable The data table of the employees that must exist in the system.
   * @author BILGUUN TEGSHBAYAR
   * Corrections made by Samy Harras
   */
  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String, String>> rows = dataTable.asMaps();
	  for (var row : rows) {
		  String username = row.get("username");
		  String name = row.get("name");
		  String password = row.get("password");
		  String phoneNumber = row.get("phoneNumber");
		  wareflow.addEmployee(username, name, password, phoneNumber);
	  }
  }

  
  /**
   * Gherkin step definition to add the manager to the WareFlow application.
   * @param dataTable The data table of the manager that must exist in the system.
   * @author BILGUUN TEGSHBAYAR
   * Corrections made by Samy Harras
   */
  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(io.cucumber.datatable.DataTable dataTable) {
      Manager manager = new Manager(dataTable.row(1).get(0), null, dataTable.row(1).get(1), null, wareflow);
      if (!wareflow.hasManager()) {
          wareflow.setManager(manager);
      }
      error="";
  }

  
  /**
   * Gherkin step definition to add items to the WareFlow application.
   * @param dataTable The data table representing the item that must exist in the system.
   * @author BILGUUN TEGSHBAYAR
   */
  @Given("the following items exist in the system")
  public void the_following_items_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String, String>> rows = dataTable.asMaps();
	    for (var row : rows) {
	      String name = row.get("name");
	      int lifespan;
	      try {
	        String expectedlifespan = row.get("expectedLifeSpanInDays");
	        lifespan = Integer.parseInt(expectedlifespan);
	      } catch (Exception e){
	        String expectedlifespan = row.get("expectedLifeSpan");
	        lifespan = Integer.parseInt(expectedlifespan);
	      }
	      ItemType itemtype = new ItemType(name, lifespan, wareflow);
	      wareflow.addItemType(itemtype);
	    }
  }


  /**
   * Gherkin step definition to set the state of shipment orders that are in the WareFlow application.
   * @param ticketId the order id number.
   * @param status the current state of the order (Open - Assigned - InProgress - Completed - Closed)
   * @author Samy Harras
   */
  @Given("order {string} is marked as {string} with requires approval {string}")
  public void order_is_marked_as_with_requires_approval(String orderId, String status,String toBeApproved) {
      ShipmentOrder currentOrder = wareflow.getOrder(Integer.parseInt(orderId)-1);
      if (Boolean.parseBoolean(toBeApproved)) {
          currentOrder.setOrderApprover(wareflow.getManager());
      }
      switch (status)
      {
          case "Open":
              break;
          case "Assigned":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              break;
          case "InProgress":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              currentOrder.startShipmentOrder();
              break;
          case "Completed":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),true);
              currentOrder.setOrderApprover(wareflow.getManager());
              currentOrder.startShipmentOrder();
              currentOrder.completeShipmentOrder();
              break;
          case "Closed":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              currentOrder.startShipmentOrder();
              currentOrder.completeShipmentOrder();
              currentOrder.approveShipmentOrder();
              break;
          default:
              break;
      }
  }
  
  
  /**
   * Gherkin step definition to add containers to the WareFlow application.
   * @param dataTable The data table representing the containers that must exist in the system.
   * @author Ismail Sentissi
   * Corrections made by Samy Harras
   */
  @Given("the following containers exist in the system")
  public void the_following_containers_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) throws java.text.ParseException {

    List<Map<String, String>> rows = dataTable.asMaps();
    
    for (var row : rows) {
      String containernumber = row.get("containerNumber");
      String type = row.get("type");
      String purchasedateString = row.get("purchaseDate");
      Date date = Date.valueOf(purchasedateString);
      String areanumber = row.get("areaNumber");
      String slotnumber = row.get("slotNumber");
      ItemType itemType = ItemType.getWithName(type);
      wareflow.addItemContainer(Integer.parseInt(containernumber), Integer.parseInt(areanumber), Integer.parseInt(slotnumber), date, itemType);

    }
  }

  /**
   * Gherkin step definition to add shipment orders to the WareFlow application.
   * @param dataTable The data table representing the orders that must exist in the system.
   * @author Samy Harras
   */
  @Given("the following orders exist in the system")
  public void the_following_orders_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
	    for (Map<String, String> row : rows) {
	        int id = Integer.parseInt(row.get("id"));
	        String orderPlacer = row.get("orderPlacer");
	        Date placedOnDate = Date.valueOf(row.get("placedOnDate"));
	        String description = row.get("description");
	        int containerNumber = Integer.parseInt(row.get("containerNumber"));
	        int quantity = Integer.parseInt(row.get("quantity"));
	        String status = row.get("status");
	        ShipmentOrder newOrder = wareflow.addOrder(id, placedOnDate, description, quantity, User.getWithUsername(orderPlacer));
	        if(containerNumber >= 0 && containerNumber < wareflow.getItemContainers().size()) {
	            ItemContainer container = wareflow.getItemContainer(containerNumber);
	            newOrder.setContainer(container);
	        } else {
	        	return;
	        }
	    }
  }


	/**
	* Gherkin step definition to add shipment notes to the WareFlow application.
	* @param dataTable The data table representing the notes that must exist in the system.
	* @author Ismail Sentissi
	* Corrections made by Samy Harras
	*/
  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	  
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String notetaker = row.get("noteTaker");
      int orderid = Integer.parseInt(row.get("orderId"));
      Date addedondate = Date.valueOf(row.get("addedOnDate"));
      String description = row.get("description");
      ShipmentOrder order = ShipmentOrder.getWithId(orderid);
      order.addShipmentNote(addedondate, description,(WarehouseStaff) User.getWithUsername(notetaker));
    }
  }

  /**
   * Gherkin step definition to set the state of shipment orders that are in the WareFlow application.
   * @param orderId the order id number.
   * @param status the current state of the order (Open - Assigned - InProgress - Completed - Closed)
   * @author Samy Harras
   */
  @Given("order {string} is marked as {string}")
  public void order_is_marked_as(String orderId, String status) {
      ShipmentOrder currentOrder = wareflow.getOrder(Integer.parseInt(orderId)-1);
      switch (status)
      {
          case "Open":
              break;
          case "Assigned":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              break;
          case "InProgress":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              currentOrder.startShipmentOrder();
              break;
          case "Completed":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),true);
              currentOrder.setOrderApprover(wareflow.getManager());
              currentOrder.startShipmentOrder();
              currentOrder.completeShipmentOrder();
              break;
          case "Closed":
              currentOrder.assignShipmentOrder(wareflow.getManager(), currentOrder.getTimeToFullfill(),currentOrder.getPriority(),currentOrder.toBeApproved());
              currentOrder.startShipmentOrder();
              currentOrder.completeShipmentOrder();
              currentOrder.approveShipmentOrder();
              break;
          default:
              break;
      }
  }

  
  /**
   * Gherkin step definition to assign maintenance tickets hotel staff, with specifications like the time to resolve, priority and if approval is required for closing.
   * @author BILGUUN TEGSHBAYAR
   */
  @When("the manager attempts to view all shipment orders in the system")
  public void the_manager_attempts_to_view_all_shipment_orders_in_the_system() {
    orders = wareflow.getOrders();
  }

  /**
   * Gherkin step definition to simulate the warehouse staff attempting to start working on an order (order MUST be in "Assigned" state).
   * @param orderId the order id number.
   * @author Samy Harras
   */
  @When("the warehouse staff attempts to start the order {string}")
  public void the_warehouse_staff_attempts_to_start_the_order(String orderId) {
	  error = ShipmentOrderController.startShipmentOrder(Integer.parseInt(orderId));
  }

  /**
   * Gherkin step definition to assign shipment orders to warehouse staff, with specifications like the time to complete, priority and if approval is required for closing.
   * @param orderId the order id number.
   * @param username the username of the warehouse staff assigned to pick the order.
   * @param timeToComplete the estimated time to complete the order.
   * @param priority the priority level of the order.
   * @param toBeApproved if the manager approval is required to close the order or not.
   * @author Samy Harras
   */
  @When("the manager attempts to assign the order {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_order_to_with_estimated_time_priority_and_requires_approval(
      String orderId, String username, String timeToComplete, String priority, String toBeApproved) {
      int id = Integer.parseInt(orderId);
      WarehouseStaff orderPicker = (WarehouseStaff) WarehouseStaff.getWithUsername(username);
      ShipmentOrder.TimeEstimate timeEstimateEnum = ShipmentOrder.TimeEstimate.valueOf(timeToComplete);
      ShipmentOrder.PriorityLevel priorityLevelEnum = ShipmentOrder.PriorityLevel.valueOf(priority);
      Boolean requiresApprovalBool = null;
      if (toBeApproved != null) {
          requiresApprovalBool = Boolean.parseBoolean(toBeApproved);
      }
      if (id<=wareflow.getOrders().size()) System.out.println(wareflow.getOrder(id-1).getStatus().toString());
      error = ShipmentOrderController.assignShipmentOrder(id, orderPicker, timeEstimateEnum, priorityLevelEnum, requiresApprovalBool);
  }

  
  /**
   * Gherkin step definition to simulate warehouse staff attempting to mark a ticket as "Closed".
   * @param orderId the order id number.
   * @author Samy Harras
   */
  @When("the warehouse staff attempts to complete the order {string}")
  public void the_warehouse_staff_attempts_to_complete_the_order(String orderId) {
	  error = ShipmentOrderController.completeShipmentOrder(Integer.parseInt(orderId));
  }

  
  /**
   * Gherkin step definition to simulate the manager attempting to disapprove a completed ticket, making it "In Progress".
   * @param orderId the order id number.
   * @param date the date at which the order completion was disapproved.
   * @param reason the reason why the order completion was disapproved.
   * @author Samy Harras
   */
  @When("the manager attempts to disapprove the order {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_order_on_date_and_with_reason(String orderId,String date, String reason) {
	  error = ShipmentOrderController.disapproveShipmentOrder(Integer.parseInt(orderId), Date.valueOf(date) , reason);
  }

  /**
   * Gherkin step definition to simulate the manager attempting to approve a completed ticket, making it "Closed".
   * @param orderId the order id number.
   * @author Samy Harras
   */
  @When("the manager attempts to approve the order {string}")
  public void the_manager_attempts_to_approve_the_order(String orderId) {
	  error = ShipmentOrderController.approveShipmentOrder(Integer.parseInt(orderId));
  }

  
  /**
   * Gherkin step definition to present orders that exist in the system
   * @authors Samy Harras
   * @param dataTable
   */  @Then("the following shipment orders shall be presented")
  public void the_following_shipment_orders_shall_be_presented(io.cucumber.datatable.DataTable dataTable) {
	    List<Map<String, String>> orderTable = dataTable.asMaps();
	    for (Map<String, String> row : orderTable) {
            int orderID = Integer.parseInt(row.get("id"));
            ShipmentOrder order = wareflow.getOrder(orderID - 1);
            String name = row.get("assetName");
            Date placedDate = Date.valueOf(row.get("placedOnDate"));
            String description = row.get("description");
            String orderPlacer = row.get("orderPlacer");
            int quantity = Integer.parseInt(row.get("quantity"));
            String status = row.get("status");

            // Assuming you have a method in WareFlowApplication to convert a ShipmentOrder to TOShipmentOrder
            
            order_is_marked_as(String.valueOf(orderID), status);
            TOShipmentOrder toorder = ShipmentOrderController.convertToTOShipmentOrder(order);
            
            assertEquals(orderID, toorder.getId());
            //assertEquals(name, toorder.getItemName());
            assertEquals(placedDate, toorder.getPlacedOnDate());
            assertEquals(description, toorder.getDescription());
            assertEquals(orderPlacer, toorder.getOrderPlacer());
            assertEquals(quantity, toorder.getQuantity());
            assertEquals(status, toorder.getStatus());
	    }
  }
  
  
//Author : Ismail Sentissi

  @Then("the order with id {string} shall have the following notes")
  public void the_order_with_id_shall_have_the_following_notes(String orderId, io.cucumber.datatable.DataTable dataTable) {
	  List<Map<String, String>> expectedNotes = dataTable.asMaps(String.class, String.class);
	  List<ShipmentNote> actualNotes = wareflow.getOrder(Integer.parseInt(orderId)-1).getShipmentNotes();

	  for (int i = 0; i < actualNotes.size(); i++) {
	      ShipmentNote actualNote = actualNotes.get(i);
	      Map<String, String> expectedNote = expectedNotes.get(i);
	      assertEquals(expectedNote.get("noteTaker"), actualNote.getNoteTaker().getUsername());
	      assertEquals(Date.valueOf(expectedNote.get("addedOnDate")), actualNote.getDate());
	      assertEquals(expectedNote.get("description"), actualNote.getDescription());
	  }
  }

  
  
  /**
   * @author Ismail Sentissi
   * @param orderId the order id number.
   */
  @Then("the order with id {string} shall have no notes")
  public void the_order_with_id_shall_have_no_notes(String orderId) {
    order = wareflow.getOrder(Integer.parseInt(orderId)-1);
    List<ShipmentNote> notes = order.getShipmentNotes();
    for (ShipmentNote note : notes) {
      note.delete();
    }
	Assert.assertFalse(wareflow.getOrder(Integer.parseInt(orderId)-1).hasShipmentNotes());
  }
  
  
  /**
   * @author Ismail Sentissi
   * @param orderId the order id number.
   */
  @Then("the order {string} shall not exist in the system")
  public void the_order_shall_not_exist_in_the_system(String orderId) {
	  Assert.assertEquals(false, ShipmentOrder.hasWithId(Integer.parseInt(orderId)));
  }


  /**
   * @author Ismail Sentissi
   * @param numberOfOrders the expected amount of orders in the system.
   */
  @Then("the number of orders in the system shall be {string}")
  public void the_number_of_orders_in_the_system_shall_be(String numberOfOrders) {
	  assertEquals(Integer.parseInt(numberOfOrders), wareflow.getOrders().size());
  }
  
  
  /**
   * @author Ismail Sentissi
   * @param expectedError the error message that the system should contain.
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String expectedError) {
	  Assert.assertEquals(expectedError, error);
  }


  /**
   * @author Ismail Sentissi
   * Corrections made by Samy Harras
   * @param orderId the order id number.
   * @param status the current status of the order.
   */
  @Then("the order {string} shall be marked as {string}")
  public void the_order_shall_be_marked_as(String orderId, String status) {
      ShipmentOrder currentOrder = wareflow.getOrder(Integer.parseInt(orderId)-1);
      order_is_marked_as(orderId, status);
      if (currentOrder.getStatus()!= null){
          Assert.assertEquals(ShipmentOrder.Status.valueOf(status), currentOrder.getStatus());
      }
  }


  /**
   * @author Ismail Sentissi
   * Corrections made by Samy Harras
   * @param orderId the order id number.
   * @param username the username of the order picker
   */
  @Then("the order {string} shall be assigned to {string}")
  public void the_order_shall_be_assigned_to(String orderId, String username) {
      ShipmentOrder currentOrder = wareflow.getOrder(Integer.parseInt(orderId)-1);
      Assert.assertEquals(WarehouseStaff.getWithUsername(username), currentOrder.getOrderPicker());
  }


  /**
   * @author Samy Harras
   * @param orderId the order id number.
   * @param timeToComplete the estimated time to complete the order.
   * @param priority the priority level of the order.
   * @param toBeApproved if the manager approval is required to close the order or not.
   */
  @Then("the order {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_order_shall_have_estimated_time_priority_and_requires_approval(String orderId,String timeToComplete, String priority, String toBeApproved) {
      ShipmentOrder currentOrder = ShipmentOrder.getWithId(Integer.parseInt(orderId));

      Assert.assertEquals(ShipmentOrder.TimeEstimate.valueOf(timeToComplete), currentOrder.getTimeToFullfill());
      Assert.assertEquals(ShipmentOrder.PriorityLevel.valueOf(priority), currentOrder.getPriority());
      if (toBeApproved != null)
      {
          Assert.assertEquals(Boolean.parseBoolean(toBeApproved), currentOrder.hasOrderApprover());
      }
  }
}