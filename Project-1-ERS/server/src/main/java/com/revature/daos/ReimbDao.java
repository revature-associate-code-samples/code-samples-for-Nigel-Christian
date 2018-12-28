package com.revature.daos;

import com.revature.models.Reimb;


public interface ReimbDao {

ReimbDao currentImplementation = new ReimbDaoJdbc();
	
	int nextId();
	//save new request
	int save(Reimb r);
	//update a request
	int update(int rId, int StatusId);
	//get managers Id to pass into resolver
	int findBoss();
}
