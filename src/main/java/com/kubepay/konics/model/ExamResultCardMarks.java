package com.kubepay.konics.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExamResultCardMarks implements Serializable {

  private static final long serialVersionUID = 3414637329847666772L;
  
  private String rollNumber=null;
  
  private String studentName = null;
  
  private String marksObtained=null;
  

}
