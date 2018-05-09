package com.kubepay.konics.service;

import java.util.List;

import com.kubepay.konics.error.BatchBusinessException;
import com.kubepay.konics.model.BatchDto;

public interface BatchService {

  List<BatchDto> getBatchData() throws BatchBusinessException;

  BatchDto getBatchById(Long id) throws BatchBusinessException;

  void delete(Long id) throws BatchBusinessException;

  Long saveOrUpdateBatch(BatchDto batch) throws BatchBusinessException;

}
