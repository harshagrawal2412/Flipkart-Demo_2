package com.flipkart.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.flipkart.bean.Customer;
import com.flipkart.bean.Gym;
import com.flipkart.bean.Slot;
import com.flipkart.business.CustomerBusiness;
import com.flipkart.business.UserBusiness;

public class CustomerClient {

	Customer customer = new Customer();
	CustomerBusiness customerBusiness = new CustomerBusiness();
	Scanner sc = new Scanner(System.in);

	public boolean registerCustomer() {
		System.out.print("Enter email: ");
		customer.setEmail(sc.next());
		System.out.print("Enter password: ");
		customer.setPassword(sc.next());
		System.out.print("Enter Name: ");
		customer.setName(sc.next());
		System.out.print("Enter Phone Number: ");
		customer.setPhoneNumber(sc.next());
		System.out.print("Enter Age: ");
		customer.setAge(Integer.valueOf(sc.next()));
		System.out.print("Enter Address: ");
		customer.setAddress(sc.next());
		UserBusiness userBusiness = new UserBusiness();
		
		boolean isRegistered =userBusiness.registerCustomer(customer);
		if(isRegistered) {
			System.out.println("Customer registered successfully!");
			return true;
		}
		else {
			System.out.println("User Already Exists");
			return false;
		}
	}

	public void viewGyms() {
		getGyms();
		
	}
	
	public void bookSlot(String email) throws ParseException {
		getGyms();
		System.out.print("Enter gym ID: ");
		String gymId = sc.next();
		System.out.print("Enter Date (yyyy-mm-dd): ");
		String dateStr = sc.next();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateStr);

		List<Slot> slots = customerBusiness.getSlotInGym(gymId);
		for (Slot slot : slots) {
			System.out.print("Slot Id: " + slot.getSlotId());
			System.out.print("Availability: " + customerBusiness.isSlotBooked(slot.getSlotId(), date));
		}
		System.out.print("Enter the slot ID which you want to book: ");
		String slotId = sc.next();
		int bookingResponse = customerBusiness.bookSlot(gymId,slotId, email, date);
		switch (bookingResponse) {
		case 0:
			System.out.println("You have already booked this time. Cancelling the previous one and booking this slot");
			break;
		case 1:
			System.out.println("Slot is already booked.");
			break;
		case 2:
			System.out.println("Successfully booked the slot");
			break;
		case 3:
			System.out.println("Slot not Exist.");
			break;
		default:
			System.out.println("Booking failed");
		}
	}

	public void editProfile(String email) {
		System.out.print("Enter password: ");
		customer.setPassword(sc.next());
		System.out.print("Enter Name: ");
		customer.setName(sc.next());
		System.out.print("Enter Phone Number: ");
		customer.setPhoneNumber(sc.next());
		System.out.print("Enter Age: ");
		customer.setAge(Integer.valueOf(sc.next()));
		System.out.print("Enter Address: ");
		customer.setAddress(sc.next());
		System.out.println("Successfully edited your profile");
	}

	public void getGyms() {
	    List<Gym> gyms = customerBusiness.getGyms();

	    // Print table header
	    System.out.println("+-------------+------------------------+-------------+");
	    System.out.println("| Gym Id      | Gym Owner Email        | Gym Name    |");
	    System.out.println("+-------------+------------------------+-------------+");
	    for (Gym gym : gyms) {
	        System.out.printf("| %-11s | %-22s | %-11s |%n", gym.getGymId(), gym.getOwnerEmail(), gym.getGymName());
	        System.out.println("+-------------+------------------------+-------------+");
	    }

	}

	public void cancelBooking(String email) {
		System.out.print("Enter booking ID that you want to cancel: ");
		String bookingId = sc.next();
		customerBusiness.cancelBooking(bookingId, email);
	}

	public void customerMenu(String email) throws ParseException {
		int choice = 0;

		while (choice != 5) {
			System.out.println("Menu:-");
			System.out.println("1.View Gyms \n2.BookSlot \n3.View Booked Slots \n4.Cancel Booked Slots \n5. Edit Profile \n6.Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				viewGyms();
				break;
			case 2:
				bookSlot(email);
				break;
			case 3:
				customerBusiness.getBookings(email);
				break;
			case 4:
				cancelBooking(email);
				break;
			case 5:
				editProfile(email);
				break;
			case 6:
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}
}