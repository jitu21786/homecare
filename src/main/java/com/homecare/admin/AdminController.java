package com.homecare.admin;

import com.homecare.model.ContactFormEntity;
import com.homecare.repository.ContactFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ContactFormRepository repository;

    // Login Page
    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";  // show login form
    }

    // Handle Login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Static username/password (aap DB ya Spring Security bhi use kar sakte ho)
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("adminUser", username);
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "admin-login";
        }
    }

    // Dashboard Page
	/*
	 * @GetMapping("/dashboard") public String dashboard(HttpSession session, Model
	 * model) { if (session.getAttribute("adminUser") == null) { return
	 * "redirect:/admin/login"; // if not logged in }
	 * 
	 * List<ContactFormEntity> data = repository.findAll();
	 * model.addAttribute("formData", data); return "admin-dashboard"; }
	 */
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
	/*
	 * @GetMapping("/admin/dashboard") public String
	 * viewDashboard(@RequestParam(defaultValue = "0") int page, Model model) { int
	 * pageSize = 10; // ek page par 10 record Page<ContactFormEntity> formPage =
	 * repository.findAll(PageRequest.of(page, pageSize,
	 * Sort.by("id").descending()));
	 * 
	 * model.addAttribute("formData", formPage.getContent());
	 * model.addAttribute("currentPage", page); model.addAttribute("totalPages",
	 * formPage.getTotalPages());
	 * 
	 * return "admin-dashboard"; }
	 */
    @GetMapping("/dashboard")
    public String viewDashboard(@RequestParam(defaultValue = "0") int page,
                                HttpSession session,
                                Model model) {
        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login"; // not logged in
        }

        int pageSize = 10; // ek page par 10 record
        Page<ContactFormEntity> formPage = repository.findAll(
                PageRequest.of(page, pageSize, Sort.by("id").ascending())
        );

        model.addAttribute("formData", formPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", formPage.getTotalPages());

        return "admin-dashboard";
    }

}
