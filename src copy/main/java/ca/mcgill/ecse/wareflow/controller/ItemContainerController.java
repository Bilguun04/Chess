package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.ItemContainer;
import ca.mcgill.ecse.wareflow.model.ItemType;
import ca.mcgill.ecse.wareflow.model.WareFlow;

// Author : Mohamed El-Machmouchi (all methods)
public class ItemContainerController {
  
	private static WareFlow wf = WareFlowApplication.getWareFlow();
	
	public static String addItemContainer(int containerNumber, int areaNumber, int slotNumber,
			Date addedOnDate, String itemTypeName) {

		// Check the validity of the item type
		if (!ItemType.hasWithName(itemTypeName)) {
			return "The item type does not exist";
			} // no item type with the following name
    
		// Check the validity of the area
		if (areaNumber < 0) {
			return "The area number shall not be less than 0";
		}
    
		// Check the validity of slot number
		if (slotNumber < 0) {
			return "The slot number shall not be less than 0";
		}
    
		// Check the validity of the container number
		if (containerNumber < 1) {
			return "The container number shall not be less than 1";
		}

		// Check the uniqueness of the container number and add the item container
		boolean c = ItemContainer.hasWithContainerNumber(containerNumber);
		if (c) {
			return "Error: A container with that container number already exists.";
		}
		else {
			try {

				wf.addItemContainer(containerNumber, areaNumber, slotNumber, addedOnDate,
						ItemType.getWithName(itemTypeName));
				return "";
			} 
			catch (Exception e) {
				return "Error:something went wrong";// an error occurred while trying to add item container
			}
		}
	}

  
	public static String updateItemContainer(int containerNumber, int newAreaNumber,
			int newSlotNumber, Date newAddedOnDate, String newItemTypeName) {
		
		// Check the existence of the item type
		if (!ItemType.hasWithName(newItemTypeName)) {
			return "The item type does not exist";
		}
		
		// Check the validity of the area number
		if (newAreaNumber < 0) {
			return "The area number shall not be less than 0";
		}
		
		// Check the validity of the slot number
		if (newSlotNumber < 0) {
			return "The slot number shall not be less than 0";
		}
		
		// Check the validity of the container number
		if (containerNumber < 0) {
			return "Error: container number should be positive";
		}

		// Check the existence of the container and update the item container
		boolean c = ItemContainer.hasWithContainerNumber(containerNumber);
		if (!c) {
			return "Error: no container with that container number  exist.";
		}
		
		else {
			try {
				ItemContainer.getWithContainerNumber(containerNumber).setAddedOnDate(newAddedOnDate);
				ItemContainer.getWithContainerNumber(containerNumber).setAreaNumber(newAreaNumber);
				ItemContainer.getWithContainerNumber(containerNumber)
				.setItemType(ItemType.getWithName(newItemTypeName));
				ItemContainer.getWithContainerNumber(containerNumber).setSlotNumber(newSlotNumber);
				return "";
				}
			catch (Exception e) {
				return "Error:something went wrong";
			}
		}
	}

	
	public static void deleteItemContainer(int containerNumber) {

		ItemContainer a = ItemContainer.getWithContainerNumber(containerNumber);
		if (a != null) {
			a.delete();// if a is not null meaning that a container with the following container number exist delete it.
		}
	}
}