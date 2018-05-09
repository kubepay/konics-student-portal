package com.kubepay.konics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kubepay.konics.entity.Batch;
import com.kubepay.konics.entity.Center;
import com.kubepay.konics.entity.Exam;
import com.kubepay.konics.entity.Marks;
import com.kubepay.konics.entity.Student;
import com.kubepay.konics.error.BatchBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.CenterDto;
import com.kubepay.konics.repository.BatchRepository;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.repository.ExamRepository;
import com.kubepay.konics.repository.MarksRepository;
import com.kubepay.konics.repository.StudentRepository;
import com.kubepay.konics.service.BatchService;
import com.kubepay.konics.service.SecurityService;

@Service("batchService")
public class BatchServiceImpl implements BatchService {

  private final BatchRepository batchRepository;

  private final CenterRepository centerRepository;
  
  private final StudentRepository studentRepository;
  
  private final ExamRepository examRepository;
  
  private final MarksRepository marksRepository;
  
  private final SecurityService securityService;

  @Autowired
  public BatchServiceImpl(final BatchRepository batchRepository,
      final StudentRepository studentRepository,
      final ExamRepository examRepository,
      MarksRepository marksRepository,
      final CenterRepository centerRepository,
      final SecurityService securityService) {

    this.securityService = securityService;
    this.batchRepository = batchRepository;
    this.examRepository = examRepository;
    this.studentRepository = studentRepository;
    this.marksRepository = marksRepository;
    this.centerRepository = centerRepository;
  }
  
  //called by getAllBatchesByCenter (GET /batch)
  @Transactional(
      readOnly = true)
  @Override
  public List<BatchDto> getBatchData() throws BatchBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final CenterDto centerDto = findByCenterId(center);
    final List<Batch> batchs = batchRepository.findByCenter(center);
    final List<BatchDto> list = new ArrayList<>();
    batchs.forEach(batch -> list.add(new BatchDto(batch, centerDto)));
    return list;
  }
  
  @Transactional(readOnly = true)
  private CenterDto findByCenterId(final Integer id) {

    final Center center = centerRepository.findOne(id);
    return new CenterDto(center);
  }

  //called by showUpdateBatchForm (GET /batch/{id}/update)
  //called by showBatch (GET/batch/{id})
  @Override
  @Transactional(
      readOnly = true)
  public BatchDto getBatchById(@NotNull final Long id) throws BatchBusinessException {

    final Batch batch = batchRepository.findOne(id);
    if (null == batch)
      throw new EmptyResultDataAccessException(1);
    
    final CenterDto centerDto = findByCenterId(batch.getCenter());
    return new BatchDto(batch, centerDto);
  }
  
  //called by deleteBatch (POST /batch/{id}/delete)
  @Override
  @Transactional
  public void delete(@NotNull final Long id) throws BatchBusinessException {
    
    final Batch batch = batchRepository.findOne(id);
    if(null ==batch) 
      throw new BatchBusinessException("Batch id " + id + "not found!");
    
    batchRepository.delete(batch);
    resetBatchStudentsForBatch(batch.getId());
    deleteExamsByBatch(batch.getId());
    deleteMarksByBatch(batch.getId());  
  }
  
  @Transactional
  private void deleteMarksByBatch(Long id) {

    final List<Marks> marks = marksRepository.findByBatch(id);
    marksRepository.delete(marks);
  }
  
  @Transactional
  private void deleteExamsByBatch(Long id) {
    final List<Exam> exams = examRepository.findByBatch(id);
    examRepository.delete(exams);    
  }

  //called by saveOrUpdateBatch (POST /batch)
  @Override
  @Transactional
  public Long saveOrUpdateBatch(@NotNull final BatchDto batchDto) throws BatchBusinessException {

    if (StringUtils.isBlank(batchDto.getCourseName()))
      throw new BatchBusinessException("Course Name can't be empty");

    final Integer center = securityService.findLoggedInUser().getCenter();
    final String user = securityService.findLoggedInUsername();
    final Long id = batchDto.getId();

    if (null == id) {

      final Batch batch = new Batch();
      fillBatch(batch, batchDto, center, user, false);
      final Batch updatedBatch = batchRepository.save(batch);
      return updatedBatch.getId();
    } else {
      
      final Batch batch = batchRepository.findOne(id);
      if (null == batch)
        throw new EmptyResultDataAccessException(1);

      fillBatch(batch, batchDto, center, user, true);
      final Batch updatedBatch = batchRepository.save(batch);
      return updatedBatch.getId();
    }
  }

  private void fillBatch(final Batch batch, final BatchDto batchDto, 
      final Integer center, final String user, final boolean update) {

    batch.setCourseName(cleanStr(batchDto.getCourseName()));
    batch.setSection(cleanStr(batchDto.getSection()));
    batch.setSessionYear(cleanStr(batchDto.getSessionYear()));
    batch.setStream(cleanStr(batchDto.getStream()));
    batch.setActive(batchDto.getActiveId());
    batch.setCode(cleanStr(batchDto.getCode()));
    batch.setCenter(center);
    if (update) {
      
      batch.setModifiedAt(new Date());
      batch.setModifiedBy(user);
    } else {
      
      batch.setCreatedAt(new Date());
      batch.setCreatedBy(user);
    }
  }

  private String cleanStr(String str) {

    return StringUtils.isNotBlank(str) ? StringUtils.trim(str) : null;
  }
  
  @Transactional
  public void resetBatchStudentsForBatch(final Long id) {
    
    final List<Student> students = studentRepository.findByBatch(id);
    for(final Student student: students) {
      student.setBatch(null);
      studentRepository.save(student);
    }    
  }
}
