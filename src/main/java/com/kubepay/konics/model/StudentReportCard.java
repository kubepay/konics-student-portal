package com.kubepay.konics.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class StudentReportCard implements Serializable {
  
  private static final long serialVersionUID = -253038596375749609L;
  
  private String rollNumber  = null;
  
  private String name = null;
  
  private String studentAttendance = null;
  
  private String batchAttendance = null;
  
  private String courseName = null;
  
  private String sessionYear = null;

  private String stream = null;

  private String section = null;
  
  private String center = null;
  
  
  private List<StudentReportCardMarks>  studentReportCardMarks = null;
  
  private boolean errorOccured = false;
  
  private String errorDescription = null;
  
  
  public StudentReportCard(){
    super();
  }
  
  public StudentReportCard(final String errorDescription){
    this.errorOccured = true;
    this.errorDescription = errorDescription;
  }

}

