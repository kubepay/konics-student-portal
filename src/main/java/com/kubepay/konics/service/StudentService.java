package com.kubepay.konics.service;

import java.util.List;
import java.util.Map;

import com.kubepay.konics.error.StudentBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.StudentDto;
import com.kubepay.konics.model.StudentReportCard;

public interface StudentService {

  List<StudentDto> getStudentData() throws StudentBusinessException;

  StudentDto getStudentById(Long id) throws StudentBusinessException;

  void delete(Long id) throws StudentBusinessException;

  Long saveOrUpdateStudent(StudentDto batch) throws StudentBusinessException;

  StudentReportCard getReportCardPdf(Long id) throws StudentBusinessException;

  Map<Long, BatchDto> getbatchMap() throws StudentBusinessException;

}
