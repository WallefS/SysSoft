package br.ueg.posse.soft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerGeneral {
	
	@RequestMapping("/home")
	public String novo() {
		return "/home";
	
	}

}
