package ca.mcgill.ecse.wareflow.controller;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.*;
import java.util.*;


// Author: Ismail Sentissi and Samy Harras (fixed add and update methods)
public class ItemTypeController {
	
	private static WareFlow wareFlow = WareFlowApplication.getWareFlow();
	
	// Authors: Samy Harras (Validity of information) and Ismail Sentissi (adding the item type)
	public static String addItemType(String name, int expectedLifeSpanInDays) {
		
		// Check validity of expected life span
		if (expectedLifeSpanInDays <= 0) {
			return "The expected life span must be greater than 0 days";
		}
		
	    // Check for non-empty name
	    if (name.trim().isEmpty()) {
	        return "The name must not be empty";
	    }
	    
	    // Check for existing item type with the same name to avoid duplicates
	    for (ItemType itemType : wareFlow.getItemTypes()) {
	        if (itemType.getName().equals(name)) {
	            return "The item type already exists";
	        }
	    }
	    
	    // If passed all checks, attempt to add the new item type
	    ItemType newItemType = new ItemType(name, expectedLifeSpanInDays, wareFlow);
	    if (wareFlow.addItemType(newItemType)) {
	        return "Successfully added new ItemType.";
	    }
	    else {
	        return "Failed to add the item type for an unexpected reason";
	    }
	}


	
	// Author: Samy Harras (Validity) and Ismail Sentissi (update of item type)
	public static String updateItemType(String oldName, String newName, int newExpectedLifeSpanInDays) {

		// Check the validity of the expected life span
		if (newExpectedLifeSpanInDays <= 0) {
			return "The expected life span must be greater than 0 days";
		}
		
		// Check the validity of the name
		if (newName.trim().isEmpty()) {
			return "The name must not be empty";
		}
	  
	    // Check the uniqueness of the item type
		if (wareFlow.hasItemTypes()) {
	        for (var item : wareFlow.getItemTypes()) {
	            if (!item.getName().equals(oldName) && item.getName().equals(newName)) {
	                return "The item type already exists";
	            }
	        }
	    }
	  
		// Update the item type
	    if (wareFlow.hasItemTypes()) {
	        for (var item : wareFlow.getItemTypes()) {
	            if (item.getName().equals(oldName)) {
	                if (item.setName(newName) && item.setExpectedLifeSpanInDays(newExpectedLifeSpanInDays)) {
	                    return "ItemType updated successfully.";
	                } else {
	                    return "ItemType didn't update successfully.";
	                }
	            }
	        }
	    }
	    return "The item type does not exist";
	}

	// Helper method to find the item type by name
	private static ItemType getItemTypeByName(String name) {
		if (wareFlow.hasItemTypes()) {
				for (ItemType item : wareFlow.getItemTypes()) {
						if (item.getName().equals(name)) {
								return item;
						}
				}
		}
		return null;
	}

 
	public static String deleteItemType(String name) {
		ItemType itemtodelete = getItemTypeByName(name);
		
		// Delete the item type
		for (ItemType item : wareFlow.getItemTypes()) {
			if (item == itemtodelete) {
				item.delete();
				return "ItemType deleted successfully.";
			}
		}
		return "No item types exist."; // If there are no item types in the system at all
	}
}