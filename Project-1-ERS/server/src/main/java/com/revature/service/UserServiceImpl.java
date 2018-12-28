package com.revature.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.daos.UserDao;
import com.revature.dto.Credential;
import com.revature.models.UserRole;
import com.revature.models.Users;

public class UserServiceImpl implements UserService{

	private UserDao ud = UserDao.currentImplementation;

	@Override
	public List<Users> findAll() {
		return ud.findAll();
	}

	@Override
	public int save(Users u) {
		// TODO Auto-generated method stub
		return ud.save(u);
	}

	@Override
	public int nextId() {
		// TODO Auto-generated method stub
		return ud.nextId();
	}
	@Override
	public List<Users> findById(int id) {
		return ud.findById(id);
	}
	//handle manager vs employee login logic here
	//combine role with user id and get a string with both
	@Override
	public boolean login(Credential cred, HttpSession session) {
		Users u = ud.findByUsernameAndPassword(cred.getUsername(), cred.getPassword());
		if (u != null ) {
			 session.setAttribute("role", u.getRole().getName()+"-"+u.getUserId());
			 return true;
		} 
		return false;
	}
	
}
