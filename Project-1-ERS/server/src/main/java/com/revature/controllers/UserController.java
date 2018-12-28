package com.revature.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.Credential;
import com.revature.models.Users;
import com.revature.service.UserService;
import com.revature.util.ResponseMapper;

public class UserController {
	private Logger log = Logger.getRootLogger();
	private UserService us = UserService.currentImplementation;
	private ObjectMapper om = new ObjectMapper();

	void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String method = req.getMethod();
		switch (method) {
		case "GET":
			processGet(req, resp);
			break;
		case "POST":
			processPost(req, resp);
			break;
		case "OPTIONS":
			return;
		default:
			resp.setStatus(404);
			break;
		}
	}

	private void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();
		String context = "";
		uri = uri.substring(context.length() + 1, uri.length());
		String[] uriArray = uri.split("/");
		System.out.println(Arrays.toString(uriArray));
		if (uriArray.length == 1) {
			//put the role and user id into array
			//if manager login, they can get all data
			//if employee get the id and pass into findby id method, voila!!!
			String role = (String) req.getSession().getAttribute("role");
			String[] roleArr = role.split("-");
			log.info(Arrays.toString(roleArr));
			if ("manager".equals(roleArr[0])) {
				log.info("retreiving all user data");
				List<Users> users = us.findAll();
				ResponseMapper.convertAndAttach(users, resp);
				return;
			} else {
				log.info("access denied");
				resp.setStatus(403);
			}
		} else if (uriArray.length == 2) {
			String role = (String) req.getSession().getAttribute("role");
			String[] roleArr = role.split("-");
			int userId = Integer.parseInt(roleArr[1]);
			//int id = Integer.parseInt(uriArray[1]); postman testing
			int id = userId;
			
			if ("employee".equals(roleArr[0])) {
				try {
				
					log.info("retreiving data for user id: " + id);
					List<Users> user = us.findById(id);
					ResponseMapper.convertAndAttach(user, resp);
					return;
				} catch (NumberFormatException e) {
					resp.setStatus(400);
					return;
				}
			} else {
				log.info("access denied");
				resp.setStatus(403);
			}
			
		} else {
			resp.setStatus(404);
		}
	}

	private void processPost(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		String uri = req.getRequestURI();
		
		String context = "";
		uri = uri.substring(context.length() + 1, uri.length());
		if ("users".equals(uri)) {
			log.info("saving new user");
			Users u = om.readValue(req.getReader(), Users.class);
			us.save(u);
			resp.setStatus(200);
			log.info("saved new sucessfully");
			return;
		} else if ("users/login".equals(uri)) {
			log.info("attempting to log in");
			Credential cred = om.readValue(req.getReader(), Credential.class);
			if(!us.login(cred, req.getSession())) {
				resp.setStatus(403);
				log.info("login unsuccessful");
			} else if(us.login(cred, req.getSession())) {
				log.info("login successful");
			}
			
		} else if ("users/logout".equals(uri)) {
			String role = (String) req.getSession().getAttribute("role");
			req.getSession().invalidate();
			resp.setStatus(200);
			log.info(role + " logged out");
		}else {
			resp.setStatus(404);
			return;
		} 
		
	}
}
