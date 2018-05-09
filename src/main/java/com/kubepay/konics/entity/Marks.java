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

@Entity
@Table(
    name = "marks")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Marks implements Serializable {

  private static final long serialVersionUID = -5853858978165339636L;

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id")
  private Long id;

  @Column(
      name = "student",
      nullable = false)
  private Long student;

  @Column(
      name = "exam",
      nullable = false)
  private Long exam;

  @Column(
      name = "center",
      nullable = false)
  private Integer center;
  
  @Column(
      name = "mark",
      nullable = true)
  private Integer mark;

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
