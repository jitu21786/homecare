package com.homecare.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import com.homecare.model.ContactFormEntity;
import com.homecare.repository.ContactFormRepository;
import org.springframework.http.ResponseEntity;


import jakarta.servlet.http.HttpServletResponse;

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
	
	@GetMapping("/form/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) throws Exception {
        ContactFormEntity form = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        // PDF Create
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

        contentStream.beginText();
        contentStream.setLeading(20f);
        contentStream.newLineAtOffset(50, 700);

        contentStream.showText("Form Details");
        contentStream.newLine();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.showText("ID: " + form.getId());
        contentStream.newLine();
        contentStream.showText("Name: " + form.getName());
        contentStream.newLine();
        contentStream.showText("Email: " + form.getEmail());
        contentStream.newLine();
        contentStream.showText("Message: " + form.getMessage());
        contentStream.newLine();
        contentStream.showText("Date: " + form.getDate());
        contentStream.endText();

        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=form_" + form.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

}
