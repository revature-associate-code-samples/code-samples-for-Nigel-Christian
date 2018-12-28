package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.models.Reimb;
import com.revature.models.Users;
import com.revature.util.ConnectionUtil;
import com.revature.util.HashingUtil;
import com.revature.util.TsUtil;

public class ReimbDaoJdbc implements ReimbDao {

	
		@Override
	public int nextId() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"SELECT (MAX(reimb_id)+1) AS nextid FROM reimbursement" 	 
			);
			
			ResultSet rs =  ps.executeQuery();
			rs.next();//need to iterate to get first (only row)
			return rs.getInt(1); //get the value in column one (well...there's only one column)
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
		//get the id of the manager to pass into reimbursements as resolver
		//could be useful in the future when manager changes
		@Override
		public int findBoss() {
			
			try (Connection conn = ConnectionUtil.getConnection()) {
				PreparedStatement ps = conn.prepareStatement(
					"SELECT MIN(ers_users_id) as manager FROM ERS_USERS\n" + 
					" LEFT JOIN USER_ROLES ON ERS_USERS.user_role_id = USER_ROLES.ers_user_role_id\n" + 
					" WHERE USER_ROLES.user_role='manager'" 	 
				);
				
				ResultSet rs =  ps.executeQuery();
				rs.next();//need to iterate to get first (only row)
				return rs.getInt(1); //get the value in column one (well...there's only one column)
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		
		
	@Override
	public int save(Reimb r) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_reciept, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) VALUES (?, ? ,?, ?, ?, ?, ?, ?, ?, ?)"
			);
			ps.setInt(1, nextId());//calling nextId() because user doesn't enter this
			ps.setDouble(2, r.getAmount());
			ps.setTimestamp(3, TsUtil.stampIt());//user won't enter time stamp, occurs at submission
			ps.setTimestamp(4, r.getResolved());//null until manager review
			ps.setString(5, r.getDescription());
			ps.setBoolean(6, false);//will go back and add receipt functionality
			ps.setInt(7, r.getAuthor());
			ps.setInt(8, findBoss());//pass in managers ID!
			//automate approval for Travel expenses less than $250
			if (r.getAmount()<250&&r.getType()==2) {
				ps.setInt(9, 2);
			} else {
				ps.setInt(9, 1);//status should always be one until updated by manager
			}
			ps.setInt(10, r.getType());
			ps.executeUpdate();
			
			return nextId();//if all goes well the next id will return for new user creation
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	
	}
	//forgot to update resolver
	//create method to get manager id and pass it in
	@Override
	public int update(int rId, int statusId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
				"UPDATE reimbursement SET reimb_resolved=?, reimb_status_id=? WHERE reimb_id=?"
			);
			
			ps.setTimestamp(1, TsUtil.stampIt());//invoke time stamp utility when manager updates status
			ps.setInt(2, statusId);//manager updates status id
			ps.setInt(3, rId);
			ps.executeUpdate();
			
			return nextId();//if all goes well the next id will return for new user creation
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	

		
}
