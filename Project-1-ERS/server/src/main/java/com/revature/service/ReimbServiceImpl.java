package com.revature.service;

import com.revature.daos.ReimbDao;
import com.revature.models.Reimb;

public class ReimbServiceImpl implements ReimbService{

	private ReimbDao rd = ReimbDao.currentImplementation;
	
	

	@Override
	public int nextId() {
		// TODO Auto-generated method stub
		return rd.nextId();
	}
	
	@Override
	public int save(Reimb r) {
		// TODO Auto-generated method stub
		return rd.save(r);
	}

	@Override
	public int update(int rId, int statusId) {
		// TODO Auto-generated method stub
		return rd.update(rId, statusId);
	}

	
	


}
