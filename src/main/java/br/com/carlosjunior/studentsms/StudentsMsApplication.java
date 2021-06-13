package br.com.carlosjunior.studentsms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.carlosjunior.studentsms.entity.Student;
import br.com.carlosjunior.studentsms.repository.StudentRepository;

@SpringBootApplication
public class StudentsMsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StudentsMsApplication.class, args);
	}

	@Autowired
	private StudentRepository studentRepository; 
	
	@Override
	public void run(String... args) throws Exception {
		/*
		Student student1 = new Student("Carlos", "Junior", "carlosjunior@carlosjunior.com.br");
		studentRepository.save(student1);

		Student student2 = new Student("Mateus", "C", "mateus@carlosjunior.com.br");
		studentRepository.save(student2);
		
		Student student3 = new Student("Eliana", "C", "Eliana@carlosjunior.com.br");
		studentRepository.save(student3);
		*/

	}

}
