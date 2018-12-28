package com.revature.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dto.Credential;
import com.revature.models.Users;


public interface UserService {

	UserService currentImplementation = new UserServiceImpl();
	//will join with all reimbursement info for manager dash board
	List<Users> findAll();
	//used for user registration
	int save(Users u);
	//pull next available id from database, pass to save()
    int nextId();
    //find user by id 
    List<Users> findById(int n);
    //credential for session
    boolean login(Credential cred, HttpSession httpSession);
   
}
