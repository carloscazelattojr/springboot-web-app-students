package br.com.carlosjunior.studentsms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.carlosjunior.studentsms.entity.Student;
import br.com.carlosjunior.studentsms.repository.StudentRepository;
import br.com.carlosjunior.studentsms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

}
