package br.com.carlosjunior.studentsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carlosjunior.studentsms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
