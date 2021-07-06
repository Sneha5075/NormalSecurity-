package com.student.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.student.dao.StudentRepository;
import com.student.entities.Student;
import com.student.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title", "this is controller");
		return "home";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("student",new Student());
		model.addAttribute("title", " this is Register page");
		return "signup";
	}
	@PostMapping("/do_register")
	public String registerUser(@ModelAttribute("student") Student student,Model model,HttpSession session)
	{
		try {
			 student.setRole("ROLE_USER");
			student.setImageUrl("default.png");
			student.setPassword(passwordEncoder.encode(student.getPassword())); 
			System.out.println("Student"+student);
			Student result = studentRepository.save(student);
			model.addAttribute("student",new Student());
			session.setAttribute("message",new Message("Successfully registered","alert-success" ));
			return "signup";
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("student",student);
			session.setAttribute("message",new Message("something went wrong!!!"+e.getMessage(),"alert-danger" ));
			return "signup";
		}
		

	}
	@RequestMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title", "this is for custom login");
		return "login";
	}
	
}