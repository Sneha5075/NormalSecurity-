package com.student.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.student.dao.StudentRepository;
import com.student.entities.Project;
import com.student.entities.Student;

@Controller
@RequestMapping("/student")
public class UserController {
	
	@Autowired
	private StudentRepository mStudentRepository;
	@ModelAttribute
	public void addCommonData(Model m,Principal  principal) {
		String email = principal.getName();
		Student student = mStudentRepository.getStudentByStudentName(email);
		m.addAttribute("student", student);
		
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model,Principal  principal) {
		/*
		 * String email = principal.getName(); Student student =
		 * mStudentRepository.getStudentByStudentName(email);
		 * model.addAttribute("student", student);
		 */
		return "normal/user_dashboard";
	}
	@GetMapping("/add_project")
	public String addcontactform(Model model) {
		
		model.addAttribute("title","Add contact");
		model.addAttribute("project", new Project());
		return "normal/add_project_form";
	}
	@PostMapping("/process-project")
	public String processProject(@ModelAttribute Project project,Principal principal) {
		String name=principal.getName();
		Student student = mStudentRepository.getStudentByStudentName(name);
		project.setStudent(student);
		student.getProject().add(project);
		mStudentRepository.save(student);
		
		//System.out.println("data"+project);
		return "normal/add_project_form";
	}
}
