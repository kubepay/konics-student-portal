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

@Data
@Entity
@Table(
    name = "exam")
@EntityListeners(AuditingEntityListener.class)
public class Exam implements Serializable {

  private static final long serialVersionUID = 8018227816442765050L;
  
  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id")
  private Long id;
      
  @Column(
      name = "name",
      length = 63,
      nullable = false)
  private String name;
  
  @Column(
      name = "subject",
      length = 63,
      nullable = false)
  private String subject;
  
  @Column(
      name = "totalmarks")
  private Integer totalMarks;
  
  @Column(
      name = "passingmarks")
  private Integer passingMarks;
  
  @Column(
      name = "preparedby",
      length = 63)
  private String preparedBy;
  
  @Column(
      name = "evaluatedby",
      length = 63)
  private String evaluatedBy;
  
  @Temporal(TemporalType.DATE)
  @Column(
      name = "dtconduct")
  private Date dateOfConduct;
  
  @Temporal(TemporalType.DATE)
  @Column(
      name = "dtresult")
  private Date dateOfResult;
  
  @Column(
      name = "remarks",
      length = 1023)
  private String remarks;
  
  @Column(
      name = "center",
      nullable = false)
  private Integer center;
  
  @Column(
      name = "batch",
      nullable = false)
  private Long batch;
  
  @Column(
      name = "active",
      nullable = false)
  private Integer active;

  @CreatedBy
  @Column(
      name = "createdby",
      length = 63,
      nullable = false)
  private String createdBy;

  @CreatedDate
  @Column(
      name = "createdat",
      nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @LastModifiedBy
  @Column(
      name = "modifiedby",
      length = 63)
  private String modifiedBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(
      name = "modifiedat")
  private Date modifiedAt;

}
