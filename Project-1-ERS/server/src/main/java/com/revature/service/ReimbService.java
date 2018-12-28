package com.revature.service;

import com.revature.models.Reimb;


public interface ReimbService {

ReimbService currentImplementation = new ReimbServiceImpl();
	
	//pull next available id from database, pass to save()
    int nextId();
  //used for saving new request
  	int save(Reimb r);
  	//update request (manager)
  	int update(int rId, int statusId);

}
