package com.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.student.dao.StudentRepository;
import com.student.entities.Student;

public class StudentDetailsServiceImpl implements UserDetailsService  {
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetching data from database
		Student studentByStudentName = studentRepository.getStudentByStudentName(username);
		
		if(studentByStudentName == null) 
			throw new UsernameNotFoundException("couldn't find the student");
		
		CustomStudentDetails customStudentDetails = new CustomStudentDetails(studentByStudentName);
		
		return customStudentDetails;
	}

}
