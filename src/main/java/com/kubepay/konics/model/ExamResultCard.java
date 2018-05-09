package com.kubepay.konics.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExamResultCard implements Serializable {

  private static final long serialVersionUID = 4681560459485858115L;
  
  private String name=null;
  
  private String subject=null;
  
  private String totalMarks=null;
  
  private String passingMarks=null;
  
  private String preparedBy=null;
  
  private String evaluatedBy=null;
  
  private String strdtConduct = null;
  
  private String strdtResult = null;
  
  private String courseName = null;
  
  private String sessionYear = null;

  private String stream = null;

  private String section = null;
  
  private String center = null;
  
  private List<ExamResultCardMarks>  examResultCardMarks = null;
  
  private boolean errorOccured = false;
  
  private String errorDescription = null;
  
  public ExamResultCard(final String errorDescription){
    this.errorOccured = true;
    this.errorDescription = errorDescription;
  }

  

}
