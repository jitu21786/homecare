package com.homecare.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeCareController {

	@GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";  // index.html ko call karega
    }

   
}
