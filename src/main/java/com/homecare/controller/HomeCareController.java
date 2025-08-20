package com.homecare.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homecare.model.ContactFormEntity;
import com.homecare.repository.ContactFormRepository;

@Controller
public class HomeCareController 
{
	private final ContactFormRepository repository;
	
	public HomeCareController(ContactFormRepository repository)
	{
		this.repository=repository;
	}

	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	@GetMapping("/service")
	public String servicePage() {
		return "service";
	}

	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}

	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}

	@GetMapping("/gallary")
	public String gallaryPage() {
		return "gallary";
	}

	@GetMapping("/service/nursing")
	public String serviceAtHome() {
		return "nursing_at_home";
	}

	@GetMapping("/service/physiotherapy")
	public String serviceAtPhysiotherapy() {
		return "physiotherapy";
	}

	@GetMapping("/service/icu")
	public String serviceAtICU() {
		return "icu_at_home";
	}

	@GetMapping("/service/diagnostics")
	public String serviceAtDiagnostics() {
		return "diagnostics";
	}

	@GetMapping("/service/attendant")
	public String serviceAttendant() {
		return "attendant";
	}

	@PostMapping("/submit")
	public String submit(@ModelAttribute ContactFormEntity form, RedirectAttributes redirectAttributes) {
	    LocalDateTime currentDateTime = LocalDateTime.now().withSecond(0).withNano(0); 
	    LocalDateTime formDateTime = form.getDate().withSecond(0).withNano(0);

	    if (!currentDateTime.equals(formDateTime)) {
	        redirectAttributes.addFlashAttribute("successMessage", "Date & Time must match current date & time!");
	        return "redirect:/contact";
	    }

	    repository.save(form);
	    redirectAttributes.addFlashAttribute("successMessage", "Form submitted successfully!");
	    return "redirect:/contact";
	}


}
