package com.kubepay.konics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kubepay.konics.entity.Center;
import com.kubepay.konics.model.CenterDto;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.service.CenterService;

@Service("centerService")
public class CenterServiceImpl implements CenterService {

  private final CenterRepository centerRepository;

  @Autowired
  public CenterServiceImpl(CenterRepository centerRepository) {

    this.centerRepository = centerRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public CenterDto findByCenterId(final Integer id) {

    final Center center = centerRepository.findOne(id);
    return new CenterDto(center);
  }

}
