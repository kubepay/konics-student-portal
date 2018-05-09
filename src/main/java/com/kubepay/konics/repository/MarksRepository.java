package com.kubepay.konics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.Marks;

@Repository("marksRepository")
public interface MarksRepository extends JpaRepository<Marks, Long> {

  List<Marks> findByCenter(Integer center);

  List<Marks> findByActive(Integer active);

  List<Marks> findByActiveAndCenter(Integer active, Integer center);
  
  List<Marks> findByStudentAndActive(Long student, Integer active);
  
  List<Marks> findByExam(Long exam);
  
  List<Marks> findByBatch(Long batch);
  
  List<Marks> findByStudent(Long student);
  
  List<Marks> findByActiveAndExam(Integer active, Long exam);

  List<Marks> findByStudentAndExamAndBatchAndCenterAndActive(Long studentId, Long examId, Long batchId, Integer centerId, Integer active);
  
  List<Marks> findByExamAndBatchAndCenterAndActive(Long examId, Long batchId, Integer centerId, Integer active);

}
