package com.spring.app.trip.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.app.trip.service.Js_TripService;

@Controller
public class Js_TripController {
	
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private Js_TripService service;
	
	@RequestMapping(value = "/test.trip")
	public String test_select (HttpServletRequest request) {
		
		
		return "home";
	}
	
	

}