package com.flipkart.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flipkart.bean.*;
import com.flipkart.bean.User;
import com.flipkart.constants.SQLConstants;
import com.flipkart.utils.DBUtils;

public class UserDAOImpl implements UserDAO {

	public boolean authenticateUser(User user) {
		// to run without authentication, make isUserValid = true
		Connection connection = null;
		
		boolean isUserValid = false;
		try {connection = DBUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.SQL_SELECT_USER_LOGIN_CREDENTIAL); 

			preparedStatement.setString(1, user.getEmail());

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				if (user.getPassword().equals(rs.getString("password"))
						&& user.getRoleId().equalsIgnoreCase(rs.getString("roleId"))) {
					System.out.println(
							rs.getString("email") + " " + rs.getString("password") + " " + rs.getString("roleId"));
					isUserValid = true;
				}
			}

		} catch (SQLException e) {
			printSQLException(e);
		}

		return isUserValid;
	}

	public boolean registerCustomer(Customer customer) {
		Connection connection = null;
		boolean registerSuccess = false;
		String query = "INSERT INTO customer VALUES (?,?,?,?,?)";
		String queryUser = "INSERT INTO user VALUES (?,?,?)";
		try {connection = DBUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				PreparedStatement preparedStatementUser = connection.prepareStatement(queryUser);
				
				preparedStatementUser.setString(1, customer.getEmail());
				preparedStatementUser.setString(2, customer.getPassword());
				preparedStatementUser.setString(3, "Customer");
				
				

				int rowsAffected = preparedStatementUser.executeUpdate();
				if (rowsAffected != 0)
					registerSuccess = true;

			preparedStatement.setString(1, customer.getEmail());
			preparedStatement.setString(2, customer.getName());
			preparedStatement.setString(3, customer.getPhoneNumber());
			preparedStatement.setInt(4, customer.getAge());
			preparedStatement.setString(5, customer.getAddress());

			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected != 0)
				registerSuccess = true;


		} catch (SQLException e) {
			System.out.println("Email already exists");
		
		}

		return registerSuccess;
	}

	public boolean registerGymOwner(GymOwner gymOwner) {
		Connection connection = null;
		boolean registerSuccess = false;
		String query = "INSERT INTO gymOwner VALUES (?,?,?,?,?,?)";
		String queryOwner = "INSERT INTO user VALUES (?,?,?)";
		try {connection = DBUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				PreparedStatement preparedStatementOwner = connection.prepareStatement(queryOwner);
				
				preparedStatementOwner.setString(2, gymOwner.getEmail());
				preparedStatementOwner.setString(1, gymOwner.getPassword());
				preparedStatementOwner.setString(3, "GymOwner");

				int rowsAffected = preparedStatementOwner.executeUpdate();
				if (rowsAffected != 0)
					registerSuccess = true;

			preparedStatement.setString(1, gymOwner.getEmail());
			preparedStatement.setString(2, gymOwner.getName());
			preparedStatement.setString(3, gymOwner.getPhoneNumber());
			preparedStatement.setString(4, gymOwner.getAadharNumber());
			preparedStatement.setString(5, gymOwner.getPanNumber());
			preparedStatement.setBoolean(6, gymOwner.isVerified());

			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected != 0)
				registerSuccess = true;

			

		} catch (SQLException e) {
			printSQLException(e);
		}

		return registerSuccess;
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}