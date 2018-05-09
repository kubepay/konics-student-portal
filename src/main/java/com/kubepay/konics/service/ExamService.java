package com.kubepay.konics.service;

import java.util.List;
import java.util.Map;

import com.kubepay.konics.error.ExamBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.ExamDto;

public interface ExamService {

  List<ExamDto> getExamData() throws ExamBusinessException;

  ExamDto getExamById(Long id) throws ExamBusinessException;

  void delete(Long id) throws ExamBusinessException;

  Long saveOrUpdateExam(ExamDto batch) throws ExamBusinessException;

  Map<Long, BatchDto> getbatchMap() throws ExamBusinessException;

}
