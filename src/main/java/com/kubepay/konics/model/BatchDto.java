package com.kubepay.konics.model;

import java.io.Serializable;

import com.kubepay.konics.entity.Batch;

import lombok.Data;

@Data
public class BatchDto implements Serializable {

  private static final long serialVersionUID = -1450712608234980746L;

  // Batch Data

  private Long id = null;

  private String courseName = null;

  private String code = null;

  private String sessionYear = null;

  private String stream = null;

  private String section = null;

  private Integer activeId = null;

  private String active = null;
  
  private Integer attendance = null;

  // Center Data

  private Integer centerId = null;

  private CenterDto center;

  public BatchDto() {

    super();
  }

  public BatchDto(final Batch b, final CenterDto center) {
    this.id = b.getId();
    this.courseName = b.getCourseName();
    this.code = b.getCode();
    this.sessionYear = b.getSessionYear();
    this.stream = b.getStream();
    this.section = b.getSection();
    this.activeId = b.getActive();
    this.attendance = b.getAttendence();
    this.active = activeTranslate(b.getActive());
    if (null != center) {
      this.center = center;
      this.centerId = center.getId();
    }

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
