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

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);		
	}

}
