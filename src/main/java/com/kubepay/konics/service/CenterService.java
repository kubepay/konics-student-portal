package com.kubepay.konics.service;

import com.kubepay.konics.model.CenterDto;

public interface CenterService {

  CenterDto findByCenterId(Integer id);

}
