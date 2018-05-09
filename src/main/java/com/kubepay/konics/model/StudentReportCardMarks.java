package com.kubepay.konics.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportCardMarks implements Serializable {

  private static final long serialVersionUID = -1079751082838576631L;
  
  private String testName=null;
  
  private String marksObtained = null;
  
  private String totalMarks=null;
  
  private String subject=null;
  
}
