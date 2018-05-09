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

/**
 * @author abhiesa
 *
 */

@Entity
@Table(
    name = "center")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Center implements Serializable {

  private static final long serialVersionUID = 4110760972356142775L;

  @Id
  @GeneratedValue(
      strategy = GenerationType.AUTO)
  @Column(
      name = "id")
  private Integer id;

  @Column(
      name = "centercode",
      length = 7,
      nullable = false,
      unique = true)
  private String centerCode;

  @Column(
      name = "centername",
      length = 63,
      nullable = false,
      unique = true)
  private String centerName;

  @Column(
      name = "active",
      nullable = false)
  private Integer active;

  @CreatedBy
  @Column(
      name = "created_by",
      length = 63,
      nullable = false,
      updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(
      name = "created_at",
      nullable = false,
      updatable = false)
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
