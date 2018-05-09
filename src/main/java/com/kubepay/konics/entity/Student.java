package com.kubepay.konics.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "student")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Student implements Serializable {

  private static final long serialVersionUID = -8756427556348565935L;

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id",
      nullable = false,
      unique = true)
  private Long id;

  @Column(
      name = "rollnumber",
      length = 31)
  private String rollNumber;

  @Column(
      name = "name",
      length = 63,
      nullable = false)
  private String name;

  @Column(
      name = "attendence",
      length = 63)
  private Integer attendence;

  @Column(
      name = "fathername",
      length = 63)
  private String fatherName;

  @Column(
      name = "mothername",
      length = 63)
  private String motherName;

  @Column(
      name = "goccupation",
      length = 31)
  private String gOccupation;

  @Temporal(TemporalType.DATE)
  @Column(
      name = "dob")
  private Date dateOfBirth;

  @Temporal(TemporalType.DATE)
  @Column(
      name = "dtjoining")
  private Date dateOfJoining;

  @Temporal(TemporalType.DATE)
  @Column(
      name = "dtleaving")
  private Date dateOfLeaving;

  @Column(
      name = "address1",
      length = 127)
  private String address1;

  @Column(
      name = "address2",
      length = 127)
  private String address2;

  @Column(
      name = "address3",
      length = 127)
  private String address3;

  @Column(
      name = "state",
      length = 31)
  private String state;

  @Column(
      name = "phone",
      length = 31)
  private String phone;

  @Column(
      name = "otherphone",
      length = 31)
  private String otherPhone;

  @Column(
      name = "mobile",
      length = 31)
  private String mobile;

  @Column(
      name = "email",
      length = 63)
  private String email;

  @Column(
      name = "category",
      length = 31)
  private String category;

  @Column(
      name = "gender",
      length = 15)
  private String gender;

  @Column(
      name = "bloodgroup",
      length = 15)
  private String bloodGroup;

  @Column(
      name = "schoolname",
      length = 127)
  private String schoolName;

  @Column(
      name = "remarks",
      length = 1023)
  private String remarks;

  @Column(
      name = "percentage",
      length = 15)
  private String percentage;

  @Column(
      name = "regno",
      length = 31,
      unique = false)
  private String regNo;

  @Column(
      name = "center",
      nullable = false)
  private Integer center;

  @Column(
      name = "batch",
      nullable = true)
  private Long batch;

  @Column(
      name = "active",
      nullable = false)
  private Integer active;

  @CreatedBy
  @Column(
      name = "created_by",
      length = 63,
      nullable = false)
  private String createdBy;

  @CreatedDate
  @Column(
      name = "created_at",
      nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedBy
  @Column(
      name = "modified_by",
      length = 63)
  private String modifiedBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(
      name = "modified_at")
  private Date modifiedAt;

}
