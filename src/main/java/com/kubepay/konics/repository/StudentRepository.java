package com.kubepay.konics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.Student;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findByBatch(Long id);
  
  List<Student> findByBatchAndActive(Long id, Integer active);
  
  List<Student> findByCenter(Integer id);
}
