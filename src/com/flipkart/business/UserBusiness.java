/**
 * 
 */
package com.flipkart.business;

import com.flipkart.DAO.GymOwnerDAOImpl;
import com.flipkart.DAO.UserDAOImpl;
import com.flipkart.bean.Customer;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.User;

/**
 * 
 */
public class UserBusiness implements UserBusinessInterface{
	
	GymOwnerDAOImpl gymOwnerDao = new GymOwnerDAOImpl();
	UserDAOImpl userDao = new UserDAOImpl();
	/**
	Registers a customer in the system.
	@param customer The Customer object representing the customer data
	*/
	public boolean registerCustomer(Customer customer) {
		boolean registerSuccess = false;
		registerSuccess = userDao.registerCustomer(customer);
		return registerSuccess;
	}
	/**
	Registers a gym owner in the system.
	@param gymOwner The gym owner object representing the gym owner data
	*/
	public boolean registerGymOwner(GymOwner gymOwner) {
		boolean registerSuccess = false;
		registerSuccess = userDao.registerGymOwner(gymOwner);
		return registerSuccess;
	}
	/**
	Verifies a user's data.
	@param user The user object representing the user data
	@return true if the user's data are valid else returns false
	*/
	public boolean authenticateUser(User user) {
		boolean authenticateSuccess = false;
		authenticateSuccess = userDao.authenticateUser(user);
		return authenticateSuccess;
	}
	/**
	Logs out a user.
	@param user The User object representing the user data
	@return true if the user is successfully logged out else returns false
	*/
	public boolean logout(User user) {
		return true;
	}
}