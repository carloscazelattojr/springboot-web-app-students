package br.com.carlosjunior.studentsms.service;

import java.util.List;

import br.com.carlosjunior.studentsms.entity.Student;

public interface StudentService {

	List<Student> getAllStudents();
	
	Student saveStudent(Student student );
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void deleteStudent(Long id);
	
}
