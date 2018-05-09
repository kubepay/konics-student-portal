package com.kubepay.konics.model;

import com.kubepay.konics.entity.Marks;

import lombok.Data;

@Data
public class MarkDto {
  
  private Long id;
  private Integer mark= null;
  private Integer active= null;
  
  private Long studentId = null;
  private String rollNumber = null;
  private String studentName = null;
  
  private Integer centerId= null;
  private Long batchId= null;
  private Long examId= null;
  
  public MarkDto(){
    super();
  }
  
  public MarkDto(final ExamDto exam, final StudentDto student) {
    this.studentId = student.getId();
    this.rollNumber = student.getRollNumber();
    this.studentName = student.getName();
    
    this.centerId = exam.getCenterId();
    this.batchId = exam.getBatchId();
    this.examId = exam.getId();
  }
  
  public MarkDto(final Marks mark, final StudentDto student) {
    this.id = mark.getId();
    this.mark=mark.getMark();
    this.active = mark.getActive();
    
    this.studentId = mark.getStudent();
    this.rollNumber = student.getRollNumber();
    this.studentName = student.getName();
    
    this.centerId = mark.getCenter();
    this.batchId = mark.getBatch();
    this.examId = mark.getExam();
  }

}