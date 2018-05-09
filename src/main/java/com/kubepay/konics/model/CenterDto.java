package com.kubepay.konics.model;

import java.io.Serializable;

import com.kubepay.konics.entity.Center;

import lombok.Data;

@Data
public class CenterDto implements Serializable {

  private static final long serialVersionUID = -5898767749233904655L;

  private Integer id;

  private String centerCode;

  private String centerName;

  private Integer activeId;
  
  private String active;

  public CenterDto() {

  }
  
  public boolean isNew() {

    return this.id == null;
  }

  public CenterDto(final Center center) {

    this.activeId = center.getActive();
    this.active = activeTranslate(this.activeId);
    this.centerCode = center.getCenterCode();
    this.centerName = center.getCenterName();
    this.id = center.getId();
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
