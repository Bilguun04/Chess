package ca.mcgill.ecse.wareflow.controller;

import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.*;

import java.util.HashMap;
import java.util.Map;


// Author : Samy Harras (for all methods)
public class UserController {
	
    private static final Map<String, User> users = new HashMap<>();
    private static WareFlow wareFlow = WareFlowApplication.getWareFlow();

    // Helper method to validate a username based on the given criteria
    private static boolean isValid(String s) {
  	  for (char c: s.toCharArray()) {
  		  int asciiCode = (int)c;
  		  if( asciiCode < 65 || (asciiCode > 90 && asciiCode < 97) || ( 57 < asciiCode && asciiCode <65 ) || asciiCode > 122) {
  			  return false;
  		  }
  	  }
  	  return true;
    }
    
    // Helper method to find any type of user in the list of users using its unique username
    private static User findUserByUsername(String username) {

        for (Employee employee : wareFlow.getEmployees()) {
            if (employee.getUsername().equalsIgnoreCase(username)) {
                return employee;
            }
        }
        for (Client client : wareFlow.getClients()) {
            if (client.getUsername().equalsIgnoreCase(username)) {
                return client;
            }
        }
        Manager manager = wareFlow.getManager();
        if (manager != null && manager.getUsername().equalsIgnoreCase(username)) {
            return manager;
        }
        return null;
    }
    
    // Helper method to update any type of user may it be an employee or client
    private static boolean updateWareFlowUser(User userToUpdate) {
        for (Employee employee : wareFlow.getEmployees()) {
            if (employee.getUsername().equalsIgnoreCase(userToUpdate.getUsername())) {
                employee.setPassword(userToUpdate.getPassword());
                employee.setName(userToUpdate.getName());
                employee.setPhoneNumber(userToUpdate.getPhoneNumber());
                return true;
            }
        }

        for (Client client : wareFlow.getClients()) {
            if (client.getUsername().equalsIgnoreCase(userToUpdate.getUsername())) {
                client.setPassword(userToUpdate.getPassword());
                client.setName(userToUpdate.getName());
                client.setPhoneNumber(userToUpdate.getPhoneNumber());
                if (userToUpdate instanceof Client) {
                    client.setAddress(((Client) userToUpdate).getAddress());
                }
                return true;
            }
        }

        return false;
    }
    
    
    //controller methods that checks the new password and updates the manager or raises an error
    public static String updateManager(String password) {
    	
    	// Check the validity of the new password
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty";
        }
        if (password.length() < 4) {
            return "Password must be at least four characters long";
        }
        if (!password.matches(".*[!#$].*")) {
            return "Password must contain one character out of !#$";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain one lower-case character";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain one upper-case character";
        }

        // Update the manager
        Manager manager = wareFlow.getManager();
        if (manager != null) {
            manager.setPassword(password);
            return "Manager password updated successfully";
        } else {
            return "Manager not found";
        }
    }

    
    //controller method that validates information for employees and clients and add them to the list of users
    public static String addEmployeeOrClient(String username, String password, String name, String phoneNumber, boolean isEmployee, String address) {
        username = username.toLowerCase();

        // Check the validity of the information of the new employee
        if ("manager".equalsIgnoreCase(username)) {
            return "Username cannot be manager";
        }

        User existingUser = findUserByUsername(username);
        if (existingUser != null) {
            return isEmployee ? "Username already linked to an employee account" : "Username already linked to a client account";
        }

        if (username.trim().isEmpty()) {
            return "Username cannot be empty";
        }
        if (password.trim().isEmpty()) {
            return "Password cannot be empty";
        }
        if (!isValid(username)) {
            return "Invalid username";
        }

        // Add employee
        if (isEmployee) {
            Employee newEmployee = wareFlow.addEmployee(username, name, password, phoneNumber);
            if (newEmployee != null) {
                users.put(username, newEmployee);
                return "Employee added successfully";
            } else {
                return "Failed to add employee";
            }
            
        // Add Client
        } else {
            Client newClient = wareFlow.addClient(username, name, password, phoneNumber, address);
            if (newClient != null) {
                users.put(username, newClient);
                return "Client added successfully";
            } else {
                return "Failed to add client";
            }
        }
    }


    //updates the information of an existing employee or client
    public static String updateEmployeeOrClient(String username, String newPassword, String newName, String newPhoneNumber, String newAddress) {
        username = username.toLowerCase();
        
        // Check the validity of employee/client and its new password
        User user = findUserByUsername(username);
        if (user == null) {
            return "User not found";
        }
        if (newPassword.trim().isEmpty()) {
        	return "Password cannot be empty";
        }

        // Set the new information of the user depending on its type
        user.setPassword(newPassword);
        user.setName(newName);
        user.setPhoneNumber(newPhoneNumber);
        if (user instanceof Client) {
            ((Client) user).setAddress(newAddress);
        }

        // Update the users
        boolean updated = updateWareFlowUser(user);
        if (!updated) {
            return "Failed to update user in WareFlow";
        }

        return "User updated successfully";
    }

    //deletes employee or client from the list of users
    public static String deleteEmployeeOrClient(String username) {
        username = username.toLowerCase();

        User userToDelete = findUserByUsername(username);

        // Check the validity of the user
        if (userToDelete == null) {
            return "User not found";
        } else if (userToDelete instanceof Manager) {
            return "Cannot delete manager account";
        
        // Delete the user
        } else {
            userToDelete.delete();
            users.remove(username);
            return "Deleted successfully";
        }
    }
}
