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
    name = "batch")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Batch implements Serializable {

  private static final long serialVersionUID = 5680376696728788885L;
  
  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id")
  private Long id;

  @Column(
      name = "coursename",
      length = 63,
      nullable = false)
  private String courseName;

  @Column(
      name = "code",
      length = 31,
      nullable = true)
  private String code;
    
  @Column(
      name = "attendence")
  private Integer attendence;

  @Column(
      name = "sessionyear",
      length = 31,
      nullable = false)
  private String sessionYear;

  @Column(
      name = "stream",
      length = 31,
      nullable = false)
  private String stream;

  @Column(
      name = "section",
      length = 31,
      nullable = false)
  private String section;

  @Column(
      name = "center",
      nullable = false)
  private Integer center;

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
