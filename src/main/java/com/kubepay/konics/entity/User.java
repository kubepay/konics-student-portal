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
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "user")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = 5086314658485962221L;

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id")
  private Integer id;

  @Column(
      name = "email",
      length = 63,
      nullable = false,
      unique = true)
  private String email;

  @Column(
      name = "password",
      length = 31,
      nullable = false)
  @Transient
  private String password;

  @Column(
      name = "name",
      length = 31,
      nullable = false)
  private String name;

  @Column(
      name = "lastname",
      length = 31)
  private String lastName;

  @Column(
      name = "active",
      nullable = false)
  private Integer active;

  @Column(
      name = "role",
      length = 31,
      nullable = false)
  private String role;

  @Column(
      name = "center",
      nullable = false)
  private Integer center;

  @CreatedBy
  @Column(
      name = "created_by",
      length = 63,
      updatable = false,
      nullable = false)
  private String createdBy;

  @CreatedDate
  @Column(
      name = "created_at",
      updatable = false,
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
