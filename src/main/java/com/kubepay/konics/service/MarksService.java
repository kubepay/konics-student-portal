package com.kubepay.konics.service;

import java.util.List;

import com.kubepay.konics.error.MarksBusinessException;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.MarkDto;

public interface MarksService {

  ExamDto getExamById(Long id) throws MarksBusinessException;

  List<MarkDto> getMarksByExamId(Long id) throws MarksBusinessException;

  List<Long> saveOrUpdateMarks(List<MarkDto> marks) throws MarksBusinessException;

  void generateTupleForExamId(Long id) throws MarksBusinessException;

}
