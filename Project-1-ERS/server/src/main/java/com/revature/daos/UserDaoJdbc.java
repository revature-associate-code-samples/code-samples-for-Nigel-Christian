package com.revature.daos;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimb;
import com.revature.models.UserRole;
import com.revature.models.Users;
import com.revature.util.ConnectionUtil;
import com.revature.util.HashingUtil;

public class UserDaoJdbc implements UserDao{

	
	private Logger log = Logger.getRootLogger();

	@Override
	public List<Users> findAll() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"SELECT \n" + 
				"	ers_users_id,\n" + 
				"	ers_username,\n" + 
				"	user_first_name,\n" + 
				"	user_last_name,\n" + 
				"	user_email,\n" + 
				"	reimb_id,\n" + 
				"	reimb_amount,\n" + 
				"	reimb_submitted,\n" + 
				"	reimb_resolved, \n" + 
				"	reimb_description,\n" + 
				"	reimb_resolver,\n" + 
				"	reimb_status,\n" + 
				"	reimb_type\n" + 
				"FROM ERS_USERS\n" + 
				"LEFT JOIN REIMBURSEMENT ON ERS_USERS.ERS_USERS_ID = REIMBURSEMENT.REIMB_AUTHOR\n" + 
				"LEFT JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.REIMB_STATUS_ID=REIMBURSEMENT_STATUS.REIMB_STATUS_ID\n" + 
				"LEFT JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.REIMB_TYPE_ID = REIMBURSEMENT_TYPE.REIMB_TYPE_ID\n WHERE REIMBURSEMENT.reimb_id > 0" + 
				"ORDER BY REIMBURSEMENT.REIMB_STATUS_ID" 
				 
			);
			
			ResultSet rs =  ps.executeQuery();
			
			List<Users> users = new ArrayList<>(); 
				while(rs.next()){
					users.add(new Users((rs.getInt("ers_users_id")),(rs.getString("ers_username")),null, (rs.getString("user_first_name")), rs.getString("user_last_name"), rs.getString("user_email"), 0,null, 
							new Reimb(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), false, 0, rs.getInt("reimb_resolver"), 0, 0, rs.getString("reimb_status"), rs.getString("reimb_type"))));
					}
				return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

		//ok...so this method will always return the next available id
		//pass into user id when saving new user (^_^)
	public int nextId() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"SELECT (MAX(ers_users_id)+1) AS nextid FROM ers_users" 	 
			);
			
			ResultSet rs =  ps.executeQuery();
			rs.next();//need to iterate to get first (only row)
			return rs.getInt(1); //get the value in column one (well...there's only one column...)
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	//when user fills out registration form a POST will send the data here
	@Override
	public int save(Users u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO ers_users (ers_users_id, ers_username,ers_password, user_first_name, user_last_name, user_email, user_role_id) VALUES (?, ? ,?, ?, ?, ?, ?)"
				
			);
			ps.setInt(1, nextId());//calling nextId() because user doesn't enter this
			ps.setString(2, u.getUsername());//user entry
			try {
				ps.setString(3, HashingUtil.hashword(u.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//no more plaintext passwords, yay!
			ps.setString(4, u.getFirstName());//user entry
			ps.setString(5, u.getLastName());//user entry
			ps.setString(6, u.getEmail());//user entry
			ps.setInt(7, 2);//user role will always be 2 because 1 is for managers
			ps.executeUpdate();
			
			return nextId();//if all goes well the next id will return for new user creation
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Users findByUsernameAndPassword(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT ers_users_id, ers_username, ers_password, user_role FROM ers_users \n" + 
					"INNER JOIN user_roles ON (ers_users.user_role_id = user_roles.ers_user_role_id) \n" + 
					"WHERE ers_username = ? AND ers_password = ? ");
			ps.setString(1, username);
			try {
				ps.setString(2, HashingUtil.hashword(password));
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			}
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Users(rs.getInt("ers_users_id"), rs.getString("ers_username"), rs.getString("ers_password"),
						null, null, null, 0, new UserRole(0, rs.getString("user_role")), null);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public List<Users> findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT \n" + 
							"	ers_users_id,\n" + 
							"	ers_username,\n" + 
							"	user_first_name,\n" + 
							"	user_last_name,\n" + 
							"	user_email,\n" + 
							"	reimb_id,\n" + 
							"	reimb_amount,\n" + 
							"	reimb_submitted,\n" + 
							"	reimb_resolved, \n" + 
							"	reimb_description,\n" +
							"	reimb_resolver,\n" + 
							"	reimb_status,\n" + 
							"	reimb_type\n" + 
							"FROM ERS_USERS\n" + 
							"LEFT JOIN REIMBURSEMENT ON ERS_USERS.ERS_USERS_ID = REIMBURSEMENT.REIMB_AUTHOR\n" + 
							"LEFT JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.REIMB_STATUS_ID=REIMBURSEMENT_STATUS.REIMB_STATUS_ID\n" + 
							"LEFT JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.REIMB_TYPE_ID = REIMBURSEMENT_TYPE.REIMB_TYPE_ID\n" + 
							"WHERE ers_users_id=? ");
			
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			List<Users> users = new ArrayList<>(); 
			while(rs.next()){
				users.add(new Users((rs.getInt("ers_users_id")),(rs.getString("ers_username")),null, (rs.getString("user_first_name")), rs.getString("user_last_name"), rs.getString("user_email"), 0,null, 
						new Reimb(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), false, 0, rs.getInt("reimb_resolver"), 0, 0, rs.getString("reimb_status"), rs.getString("reimb_type"))));
				}
			return users;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
