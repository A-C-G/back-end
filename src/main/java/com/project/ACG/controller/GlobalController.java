package com.project.ACG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {

	@GetMapping("/setting")
	public String setting() {
		return "setting";
	}

	@GetMapping("/withdraw")
	public String withdraw() {
		return "withdraw";
	}

	@GetMapping("/description")
	public String description() {
		return "description";
	}

	@GetMapping("/chat")
	public String chatting() {
		return "chat";
	}
}
