package com.kubepay.konics.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kubepay.konics.entity.Exam;

import lombok.Data;

@Data
public class ExamDto implements Serializable {

  private static final long serialVersionUID = 460711230289695158L;

  private Long id=null;
  private String name=null;
  private String subject=null;
  private Integer totalMarks=null;
  private Integer passingMarks=null;
  private String preparedBy=null;
  private String evaluatedBy=null;
  private Date dateOfConduct=null;
  private Date dateOfResult=null;
  private String remarks=null;
  private BatchDto batch=null;
  private Integer centerId = null;
  private Long batchId=null;
  private Integer activeId = null;
  private String active = null;
  private String strdtConduct = null;
  private String strdtResult = null;

  

  public ExamDto() {

    super();
  }

  public ExamDto(final Exam exam, final BatchDto batchDto) {
    this.id = exam.getId();
    this.name = exam.getName();
    this.subject = exam.getSubject();
    this.totalMarks = exam.getTotalMarks();
    this.passingMarks = exam.getPassingMarks();
    this.preparedBy = exam.getPreparedBy();
    this.evaluatedBy = exam.getEvaluatedBy();
    
    if (null !=  exam.getDateOfConduct()) {
      this.dateOfConduct =  exam.getDateOfConduct();
      this.strdtConduct = toStrFromDate(this.dateOfConduct);
    }
    
    if (null !=  exam.getDateOfResult()) {
      this.dateOfResult =  exam.getDateOfResult();
      this.strdtResult = toStrFromDate(this.dateOfResult);
    }

    this.remarks = exam.getRemarks();
    this.batch = batchDto;
    this.centerId = exam.getCenter();
    this.batchId = exam.getBatch();
    this.activeId = exam.getActive();
    this.active = activeTranslate(this.activeId);
  }
  
  public ExamDto(Exam exam, CenterDto c) {

    this.id = exam.getId();
    this.name = exam.getName();
    this.subject = exam.getSubject();
    this.totalMarks = exam.getTotalMarks();
    this.passingMarks = exam.getPassingMarks();
    this.preparedBy = exam.getPreparedBy();
    this.evaluatedBy = exam.getEvaluatedBy();
    
    if (null !=  exam.getDateOfConduct()) {
      this.dateOfConduct =  exam.getDateOfConduct();
      this.strdtConduct = toStrFromDate(this.dateOfConduct);
    }
    
    if (null !=  exam.getDateOfResult()) {
      this.dateOfResult =  exam.getDateOfResult();
      this.strdtResult = toStrFromDate(this.dateOfResult);
    }

    this.remarks = exam.getRemarks();
    this.batch = null;
    this.centerId = exam.getCenter();
    this.batchId = exam.getBatch();
    this.activeId = exam.getActive();
    this.active = activeTranslate(this.activeId);
  }

  private String toStrFromDate(final Date date) {
    final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    if (null != date)
      return df.format(date);
    return null;
  }

  public boolean isNew() {

    return this.id == null;
  }

  private String activeTranslate(final Integer active) {

    if (null == active)
      return "?";
    else if (1 == active)
      return "Y";
    else if (0 == active)
      return "N";
    else
      return "?";
  }

}
