package com.kubepay.konics.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kubepay.konics.entity.Student;

import lombok.Data;

@Data
public class StudentDto implements Serializable {

  private static final long serialVersionUID = 1526580515987156033L;

  private Long id = null;

  private String rollNumber = null;

  private String name = null;
  
  private Integer attendance = null;

  private String fatherName = null;

  private String motherName = null;

  private String occupation = null;

  private Date dateOfBirth = null;
  
  private String strdtBirth = null;

  private Date dateOfJoining = null;

  private String strdtJoining = null;
  
  private Date dateOfLeaving = null;
  
  private String strdtLeaving = null;

  private String address1 = null;

  private String address2 = null;

  private String address3 = null;

  private String state = null;

  private String phone = null;

  private String otherPhone = null;

  private String mobile = null;

  private String email = null;

  private String category = null;

  private String gender = null;

  private String bloodGroup = null;

  private String schoolName = null;

  private String remarks = null;

  private String percentage = null;

  private String regno = null;

  private Integer center = null;

  private Long batchId = null;

  private Integer activeId = null;

  private String active = null;

  private BatchDto batch = null;

  public StudentDto(final Student dto, final BatchDto batch) {

    this.id = dto.getId();
    this.rollNumber = dto.getRollNumber();
    this.name = dto.getName();
    this.attendance = dto.getAttendence();
    this.fatherName = dto.getFatherName();
    this.motherName = dto.getMotherName();
    this.occupation = dto.getGOccupation();
    
    if (null != dto.getDateOfBirth()) {
      this.dateOfBirth = dto.getDateOfBirth();
      this.strdtBirth = toStrFromDate(this.dateOfBirth);
    }
    
    if (null != dto.getDateOfJoining()) {
      this.dateOfJoining = dto.getDateOfJoining();
      this.strdtJoining = toStrFromDate(this.dateOfJoining);
    }
    
    if (null != dto.getDateOfLeaving()) {
      this.dateOfLeaving = dto.getDateOfLeaving();
      this.strdtLeaving = toStrFromDate(this.dateOfLeaving);
    }
    
    this.address1 = dto.getAddress1();
    this.address2 = dto.getAddress2();
    this.address3 = dto.getAddress3();
    this.state = dto.getState();
    this.phone = dto.getPhone();
    this.otherPhone = dto.getOtherPhone();
    this.mobile = dto.getMobile();
    this.email = dto.getEmail();
    this.category = dto.getCategory();
    this.gender = dto.getGender();
    this.bloodGroup = dto.getBloodGroup();
    this.schoolName = dto.getSchoolName();
    this.remarks = dto.getRemarks();
    this.percentage = dto.getPercentage();
    this.regno = dto.getRegNo();
    this.center = dto.getCenter();
    this.batchId = dto.getBatch();
    this.activeId = dto.getActive();
    this.active = activeTranslate(this.activeId);
    this.batch = batch;
  }
  
  private String toStrFromDate(final Date date) {
    final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    if (null != date)
      return df.format(date);
    return null;
  }
  

  public StudentDto() {

  }

  public StudentDto(Student dto, CenterDto center) {

    this.id = dto.getId();
    this.rollNumber = dto.getRollNumber();
    this.name = dto.getName();
    this.fatherName = dto.getFatherName();
    this.motherName = dto.getMotherName();
    this.occupation = dto.getGOccupation();
    this.attendance = dto.getAttendence();
    
    if (null != dto.getDateOfBirth()) {
      this.dateOfBirth = dto.getDateOfBirth();
      this.strdtBirth = toStrFromDate(this.dateOfBirth);
    }
    
    if (null != dto.getDateOfJoining()) {
      this.dateOfJoining = dto.getDateOfJoining();
      this.strdtJoining = toStrFromDate(this.dateOfJoining);
    }
    
    if (null != dto.getDateOfLeaving()) {
      this.dateOfLeaving = dto.getDateOfLeaving();
      this.strdtLeaving = toStrFromDate(this.dateOfLeaving);
    }
    
    this.address1 = dto.getAddress1();
    this.address2 = dto.getAddress2();
    this.address3 = dto.getAddress3();
    this.state = dto.getState();
    this.phone = dto.getPhone();
    this.otherPhone = dto.getOtherPhone();
    this.mobile = dto.getMobile();
    this.email = dto.getEmail();
    this.category = dto.getCategory();
    this.gender = dto.getGender();
    this.bloodGroup = dto.getBloodGroup();
    this.schoolName = dto.getSchoolName();
    this.remarks = dto.getRemarks();
    this.percentage = dto.getPercentage();
    this.regno = dto.getRegNo();
    this.center = dto.getCenter();
    this.batchId = dto.getBatch();
    this.activeId = dto.getActive();
    this.active = activeTranslate(this.activeId);
    this.batch = new BatchDto();
    this.batch.setCenter(center);
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
