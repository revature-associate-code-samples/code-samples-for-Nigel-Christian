package com.revature.controllers;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimb;
import com.revature.models.Users;
import com.revature.service.ReimbService;
import com.revature.util.ResponseMapper;

public class ReimbController {
	private Logger log = Logger.getRootLogger();
	private ReimbService rs = ReimbService.currentImplementation;
	private ObjectMapper om = new ObjectMapper();

	void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String method = req.getMethod();
		switch (method) {
		case "PATCH":
			processPatch(req, resp);
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

	
	private void processPost(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		String uri = req.getRequestURI();
		String context = "";
		uri = uri.substring(context.length() + 1, uri.length());
		if ("reimbs".equals(uri)) {
			log.info("saving new request ");
			Reimb r = om.readValue(req.getReader(), Reimb.class);
			rs.save(r);
			resp.getWriter().write("" + r.getAuthor());
			resp.setStatus(201);
			log.info("request saved ");
			return;
		}
	}
	
	
	
	private void processPatch(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		
		String uri = req.getRequestURI();
		String context = "";
		uri = uri.substring(context.length() + 1, uri.length());
		String[] uriArray = uri.split("/");
		System.out.println(Arrays.toString(uriArray));
		 if (uriArray.length == 3) {
			 String role = (String) req.getSession().getAttribute("role");
				String[] roleArr = role.split("-");
				log.info(Arrays.toString(roleArr));
				if ("manager".equals(roleArr[0])) {
					try {
						int rId = Integer.parseInt(uriArray[1]);
						int statusId = Integer.parseInt(uriArray[2]);
						log.info("updating remibursement id: " + rId + " to status: " + statusId);
						rs.update(rId, statusId);
						resp.setStatus(201);
						log.info("update completed successfully");
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
}