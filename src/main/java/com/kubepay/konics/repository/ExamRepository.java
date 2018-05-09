package com.kubepay.konics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.Exam;

@Repository("examRepository")
public interface ExamRepository extends JpaRepository<Exam, Long> {

  List<Exam> findByCenter(Integer center);

  List<Exam> findByActive(Integer active);
  
  List<Exam> findByBatch(Long batch);
  
  List<Exam> findByBatchAndActive(Long batch, Integer active);
  
  List<Exam> findByActiveAndCenter(Integer active, Integer center);
  
  List<Exam> findByNameAndSubjectAndBatchAndCenter(String name, String subject, Long batch, Integer center);

}
