package com.revature.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DispatcherServlet extends HttpServlet{

	private Logger log = Logger.getRootLogger();
	private UserController uc = new UserController();
	private ReimbController rc = new ReimbController();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		resp.addHeader("Access-Control-Allow-Origin", "http://callmekurisu.info");
		
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PATCH, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
		
		String uri = req.getRequestURI();
		String context = "";
		uri = uri.substring(context.length() + 1, uri.length());
		log.debug("request made with uri: " + uri);
		if (uri.startsWith("users")) {
			uc.process(req, resp);
		} else if (uri.equals("users/login")) {
			uc.process(req, resp);
		} else if (uri.startsWith("reimbs")) {
			rc.process(req, resp);
		} else {
			resp.setStatus(404);
		}
	}
}
