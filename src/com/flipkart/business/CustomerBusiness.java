/**
 *
 */
package com.flipkart.business;

import com.flipkart.bean.*;
import com.flipkart.utils.IdGenerator;
import com.flipkart.DAO.*;

import java.util.*;

public class CustomerBusiness implements CustomerBusinessInterface {
	
	CustomerDAOImpl cust=new CustomerDAOImpl();

	List<Customer> customers = new ArrayList<>();
	List<Booking> bookings = new ArrayList<>();

	List<Slot> slots = new ArrayList<>();
	List<Gym> gyms = new ArrayList<>();

//	Date d1 = new Date();
//	Customer customer1 = new Customer("c1@gmail.com", "c1", "Customer", "Vaishnavi", "0000", 22, "Kanpur");
//	Customer customer2 = new Customer("c2@gmail.com", "c2", "Customer", "Anjali", "0000", 32, "Vadodara");
//	Customer customer3 = new Customer("c3@gmail.com", "c3", "Customer", "Sudha", "0000", 42, "Kolkata");
//	Customer customer4 = new Customer("c4@gmail.com", "c4", "Customer", "Aaishu", "0000", 52, "Mumbai");
//
//
//	Booking b1 = new Booking("123", "121", "171", "confirmed", d1, "c1@gmail.com", "John");
//	Booking b2 = new Booking("173", "191", "131", "waitlisted", d1, "c2@gmail.com", "Jack");
//	Booking b3 = new Booking("113", "129", "173", "confirmed", d1, "c3@gmail.com", "Johnathon");
//	Booking b4 = new Booking("193", "127", "971", "waitlisted", d1, "c4@gmail.com", "J");
//
//	Slot s1 = new Slot("900", "1400", "1500", 100, "John", "g1");
//	Slot s2 = new Slot("910", "1500", "1600", 100, "J", "g2");
//	Slot s3 = new Slot("930", "1600", "1700", 100, "Jack", "g3");
//	Slot s4 = new Slot("950", "1700", "1800", 100, "Johnny", "g4");
//
//
//	Gym gym1 = new Gym("g1", "gym1", "gymowner1@gmail.com", "Kanpur", 2, 5, true);
//	Gym gym2 = new Gym("g2", "gym2", "gymowner2@gmail.com", "Hyderabad", 3, 5, true);
//	Gym gym3 = new Gym("g3", "gym3", "gymowner3@gmail.com", "Bangalore", 2, 3, true);
//	Gym gym4 = new Gym("g4", "gym4", "gymowner4@gmail.com", "Cochin", 6, 5, true);
//
//	public CustomerBusiness() {
//		customers.add(customer1);
//		customers.add(customer2);
//		customers.add(customer3);
//		customers.add(customer4);
//
//		bookings.add(b1);
//		bookings.add(b2);
//		bookings.add(b3);
//		bookings.add(b4);
//
//		slots.add(s1);
//		slots.add(s2);
//		slots.add(s3);
//		slots.add(s4);
//
//		gyms.add(gym1);
//		gyms.add(gym2);
//		gyms.add(gym3);
//		gyms.add(gym4);
//	}

	/**
	 * Obtains customer's profile details 
	 * @param customer the Customer object for which the profile details are requested
	 * @return Customer the Customer's object
	 */
	public Customer getProfile(Customer customer) {
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				return cust;
		}
		return null;
	}

	/**
	 * Gives functionality of updating customer's personal data. 
	 * @param customer the Customer object for which the profile data needs to be updated
	 */
	public void editProfile(Customer customer) {
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {
				cust.setName(customer.getName());
				cust.setPhoneNumber(customer.getPhoneNumber());
				cust.setAge(customer.getAge());
				cust.setAddress(customer.getAddress());
				customers.add(cust);
				System.out.println("Successfully edited your profile\ns");
				break;
			}
		}
	}
	/**
	 * Obtains all the bookings done by the given customer email.
	 * @param email the Customer email for which the bookings data are requested
	 * @return List of bookings done by the given customer email
	 */
	public List<Booking> getBookings(String email) {

		List<Booking> customerBookings = cust.fetchBookedSlots(email);

		return customerBookings;
	}
	/**
	 * Performs booking cancellation operation for the given customer email.
	 * @param bookingId the id of booking for which cancellation needs to be performed
	 * @param email the Customer email for which the booking cancellation is requested
	 * @return returns true of the booking gets cancelled successfully else returns false
	 */
	public boolean cancelBooking(String bookingId, String email) {

		for (Booking booking : bookings) {
			if (booking.getBookingId().equals(bookingId)) {
				bookings.remove(booking);
				System.out.println("Successfully cancelled your booking" );
				return true;
			}
		}
		return false;
	}
	/**
	 * Obtains all the gyms for the given city.
	 * @param city the city name for which the gym list is requested
	 * @return returns List of gyms available for the given city
	 */
	public List<Gym> getGyms() {
		
		List<Gym> newGym =cust.fetchGymList();
		return newGym;
	}
	/**
	 * Obtains all the slots for the given gymId.
	 * @param gymId the Gym Id for which the slot details are requested
	 * @return returns List of available slots for the given gymId
	 */
	public List<Slot> getSlotInGym(String gymId) {
		List<Slot> slotsOfGym=new ArrayList<>();
		try {
			slotsOfGym =cust.fetchSlotList(gymId);
			
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		return slotsOfGym;
	}
	/**
	 * Performs booking operation for the given customer email on the given date for the given slotId
	 * @param email the email of customer who requested the booking operation
	 * @param slotId the slot id in which the customer wants to book a seat
	 * @param date the date on which the customer wants to book a seat
	 * @return returns integer signal based on the customer's booking status
	 */
	public int bookSlot(String gymId, String slotId, String email, Date date) {
		List<Booking> tempBookings = getBookings(email);
		Boolean checkSlot=cust.checkSlotExists(slotId, gymId);
		if(!checkSlot) {
			return 3;
		}
		if(cust.isFull(slotId, date)) {
			return 2;
		}
		boolean flag=false;
		if(flag)
		{
			boolean isDate=false;
			for(Booking booking:tempBookings)
			{
				if(booking.getDate().equals(date))
				{
					isDate=true;
					for(Slot slot:slots)
					{
						if(slot.getSlotId().equals(slotId) && !slot.getGymId().equals(gymId))
						{
							int num=slot.getNumOfSeatsBooked();
							if(!isSlotBooked(slotId,date))
							{
								slot.setNumOfSeatsBooked(num--);
								Booking getBooking = new Booking();
								getBooking.setBookingId(IdGenerator.generateId("Booking"));
								Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
								bookings.add(tempBooking);
								bookings.remove(booking);
								return 0;
							}
							else
							{
								slot.setNumOfSeatsBooked(num--);
								Booking getBooking = new Booking();
								getBooking.setBookingId(IdGenerator.generateId("Booking"));
								Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
								bookings.add(tempBooking);
								return 1;
							}
						}
					}
					return 3;
				}
			}
			if(!isDate)
			{
				for(Slot slot:slots)
				{
					if(slot.getSlotId().equals(slotId) && slot.getGymId().equals(gymId))
					{
						int num=slot.getNumOfSeatsBooked();
						slot.setNumOfSeatsBooked(num--);
						Booking getBooking = new Booking();
						getBooking.setBookingId(IdGenerator.generateId("Booking"));
						if(!isSlotBooked(slotId,date))
						{
							Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
							bookings.add(tempBooking);
							return 2;
						}
						else
						{
							Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
							bookings.add(tempBooking);
							return 1;
						}
					}
				}
			}
		}
		else
		{
			for(Slot slot:slots)
			{
				if(slot.getSlotId().equals(slotId) && slot.getGymId().equals(gymId))
				{
					int num=slot.getNumOfSeatsBooked();
					slot.setNumOfSeatsBooked(num--);
					Booking getBooking = new Booking();
					getBooking.setBookingId(IdGenerator.generateId("Booking"));
					if(!isSlotBooked(slotId,date))
					{
						Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"confirmed",date,email,slot.getTrainer());
						bookings.add(tempBooking);
						return 2;
					}
					else
					{
						Booking tempBooking=new Booking(getBooking.getBookingId(),slotId,slot.getGymId(),"waitlisted",date,email,slot.getTrainer());
						bookings.add(tempBooking);
						return 1;
					}
				}
			}
			return 3;
		}
		return 1;
	}
	/**
	 * Checks if the slot is already booked or not
	 * @param slotId the slot id for which the booking status is requested
	 * @param date the date on which the booking status is requested
	 * @return returns true if the slot id for the given date is fully booked else returns false
	 */
	public boolean isSlotBooked(String slotId, Date date) {
		return cust.isFull(slotId, date);
	}
	/**
	 * Checks if the customer has already booked a seat in the same slot for the given date
	 * @param slotId the slot id for which the booking status is requested
	 * @param date the date on which the booking status is requested
	 * @param customerEmail the email of customer for which the booking status is getting checked
	 * @return returns true if the customer has already booked a seat on the same date in the same slot
	 */
	public boolean hasBookedSlotAlready(String slotId, String customerEmail, Date date) {
		for (Booking b : bookings) {
			if (b.getSlotId().equals(slotId)) {
				if (b.getCustomerEmail().equals(customerEmail))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isFull(String slotId, Date date) {
		// TODO Auto-generated method stub
		return false;
	}


}