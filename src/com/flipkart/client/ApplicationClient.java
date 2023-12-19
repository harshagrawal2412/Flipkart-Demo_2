package com.flipkart.client;

import java.util.*;

import com.flipkart.bean.User;
import com.flipkart.business.UserBusiness;

public class ApplicationClient {

	public static void login() throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("__________________________________________________________________________________\n");
		System.out.println("Enter LogIn Details\n");
		System.out.print("Enter Email: ");
		String userEmail = in.next();
		System.out.print("Enter Password: ");
		String password = in.next();
		System.out.print("Enter Role Name: ");
		String roleId = in.next();
		User user = new User(userEmail, password, roleId);
		UserBusiness userBusiness = new UserBusiness();
		if (roleId.equalsIgnoreCase("Admin")) {
			if(userBusiness.authenticateUser(user)) {
				AdminClient admin = new AdminClient();
				admin.adminMenu(in);
			}else {
				System.out.println("\nSorry! You are not Admin.");
			}
			
		} 
		else if (userBusiness.authenticateUser(user)) {
			System.out.println("__________________________________________________________________________________\n");
			System.out.println(
					"Welcome " + userEmail + "! You are logged in.");

			if (roleId.equalsIgnoreCase("Customer")) {

				CustomerClient customer = new CustomerClient();
				customer.customerMenu(userEmail);

			} else if (roleId.equalsIgnoreCase("GymOwner")) {

				GymOwnerClient gymOwner = new GymOwnerClient();
				gymOwner.gymOwnerMenu(in, userEmail);

			} else {
				System.out.println("Wrong Choice!");
			}
		} else {
			System.out.println("\nSorry! You are not Registered! Please Register Yourself!");
		}
	}

	public static void applicationMenu() throws Exception {
		boolean recur = true;
		System.out.println("Welcome to the FlipFit Application!");

		while (recur) {
			System.out.println("\nChoose your action:");
			System.out.println("1. Login");
			System.out.println("2. Customer Registration");
			System.out.println("3. Gym Owner Registration");
			System.out.println("4. Exit");
			System.out.print("\nEnter Your Choice: ");

			Scanner in = new Scanner(System.in);

			int choice = in.nextInt();
			switch (choice) {
			case 1:
				login();
				break;
			case 2:
				CustomerClient customer = new CustomerClient();
				
					boolean isRegistered=customer.registerCustomer();
					if(isRegistered) login();
					else break;
				
				break;
			case 3:
				GymOwnerClient owner = new GymOwnerClient();
				owner.gymOwnerRegistration(in);
				login();
				break;
			case 4:
				System.out.println("Exiting..." );
				System.out.println("Exited Successfully");
				recur = false;
				System.exit(0);
				break;
			default:
				System.out.println("Wrong choice");
			}
		}

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		applicationMenu();
	}

}