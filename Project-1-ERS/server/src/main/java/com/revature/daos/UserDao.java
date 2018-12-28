package com.revature.daos;

import java.util.List;

import com.revature.models.UserRole;
import com.revature.models.Users;


public interface UserDao {

	UserDao currentImplementation = new UserDaoJdbc();
	

	List<Users> findAll();
	
	int save(Users u);
	
	int nextId();

	Users findByUsernameAndPassword(String username, String password);

	List<Users> findById(int id);
}
