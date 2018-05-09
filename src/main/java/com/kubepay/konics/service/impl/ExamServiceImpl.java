package com.kubepay.konics.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.kubepay.konics.error.ExamBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.CenterDto;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.ExamResultCard;
import com.kubepay.konics.model.ExamResultCardMarks;
import com.kubepay.konics.repository.BatchRepository;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.repository.ExamRepository;
import com.kubepay.konics.repository.MarksRepository;
import com.kubepay.konics.repository.StudentRepository;
import com.kubepay.konics.service.ExamService;
import com.kubepay.konics.service.SecurityService;

@Service("examService")
public class ExamServiceImpl implements ExamService {

  private final ExamRepository examRepository;

  private final BatchRepository batchRepository;

  private final StudentRepository studentRepository;

  private final CenterRepository centerRepository;

  private final MarksRepository marksRepository;

  private final SecurityService securityService;

  @Autowired
  public ExamServiceImpl(final ExamRepository examRepository,
      final StudentRepository studentRepository,
      final BatchRepository batchRepository,
      final CenterRepository centerRepository,
      final MarksRepository marksRepository,
      final SecurityService securityService) {

    this.securityService = securityService;
    this.studentRepository = studentRepository;
    this.examRepository = examRepository;
    this.batchRepository = batchRepository;
    this.marksRepository = marksRepository;
    this.centerRepository = centerRepository;
  }

  // called by getAllExams (GET /exam)
  @Transactional(
      readOnly = true)
  @Override
  public List<ExamDto> getExamData() throws ExamBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final List<Exam> exams = examRepository.findByCenter(center);

    final List<BatchDto> batches = getBatchData();
    final Map<Long, BatchDto> batchMap = new HashMap<>();
    batches.forEach(batch -> batchMap.put(batch.getId(), batch));

    final List<ExamDto> list = new ArrayList<>();
    exams.forEach(exam -> {
      final Long key = exam.getBatch();
      final BatchDto batch = batchMap.get(key);
      list.add(new ExamDto(exam, batch));
    });
    return list;

  }

  // called by saveOrUpdateExam (POST /exam)
  @Transactional
  @Override
  public Long saveOrUpdateExam(final ExamDto examDto) throws ExamBusinessException {

    if (StringUtils.isBlank(examDto.getName()))
      throw new ExamBusinessException("Exam Name can't be empty");

    final Integer center = securityService.findLoggedInUser().getCenter();
    final String user = securityService.findLoggedInUsername();
    final Long id = examDto.getId();
    final List<Exam> foundList = examRepository.findByNameAndSubjectAndBatchAndCenter(examDto.getName(),
        examDto.getSubject(),
        examDto.getBatchId(), center);
    if (null == id) {

      if (!foundList.isEmpty()) {
        throw new ExamBusinessException("Combination of Exam Name, Subject and Batch should be unique!");
      }

      final Exam exam = new Exam();
      fillExam(exam, examDto, center, user, false);

      final Exam updatedExam = examRepository.save(exam);
      return updatedExam.getId();
    } else {
      final Exam exam = examRepository.findOne(id);
      if (foundList.size() > 1) {
        throw new ExamBusinessException("Combination of Exam Name, Subject and Batch should be unique!");
      } else if (foundList.size() == 1) {
        final Exam forValidation = foundList.get(0);
        if (forValidation.getId() != exam.getId()) {
          throw new ExamBusinessException("Combination of Exam Name, Subject and Batch should be unique!");
        }
      }

      if (null == exam)
        throw new EmptyResultDataAccessException(1);
      fillExam(exam, examDto, center, user, true);

      final Exam updatedExam = examRepository.save(exam);
      return updatedExam.getId();
    }
  }

  private void fillExam(final Exam exam, final ExamDto examDto, final Integer center, final String user,
      final boolean update) {

    exam.setActive(examDto.getActiveId());
    exam.setBatch(examDto.getBatchId());
    exam.setCenter(center);
    exam.setDateOfConduct(toDateFromStr(examDto.getStrdtConduct()));
    exam.setDateOfResult(toDateFromStr(examDto.getStrdtResult()));
    exam.setEvaluatedBy(cleanStr(examDto.getEvaluatedBy()));
    exam.setName(cleanStr(examDto.getName()));
    exam.setPassingMarks(examDto.getPassingMarks());
    exam.setPreparedBy(cleanStr(examDto.getPreparedBy()));
    exam.setRemarks(cleanStr(examDto.getRemarks()));
    exam.setSubject(examDto.getSubject());
    exam.setTotalMarks(examDto.getTotalMarks());

    if (update) {
      exam.setModifiedAt(new Date());
      exam.setModifiedBy(user);
    } else {
      exam.setCreatedAt(new Date());
      exam.setCreatedBy(user);
    }
  }

  private Date toDateFromStr(final String date) {

    final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    try {
      if (StringUtils.isNotBlank(date))
        return df.parse(date);
      return null;
    } catch (final Exception e) {
      return null;
    }
  }

  // private Date toDate(final Integer dd, final Integer mm, final Integer yyyy) {
  //
  // if (null == dd || 0 == dd)
  // return null;
  // if (null == mm || 0 == mm)
  // return null;
  // if (null == yyyy || 0 == yyyy)
  // return null;
  // try {
  // Calendar c = Calendar.getInstance();
  // c.set(yyyy, mm - 1, dd, 0, 0);
  // return c.getTime();
  // } catch (final Exception e) {
  // log.error(e.getMessage(), e);
  // return null;
  // }
  // }

  private String cleanStr(String str) {

    if (StringUtils.isNotBlank(str))
      return StringUtils.trim(str);
    else
      return null;
  }

  // called by showUpdateExamForm (GET /exam/{id}/update)
  // called by showExam (GET /exam/{id})
  @Transactional(
      readOnly = true)
  @Override
  public ExamDto getExamById(final Long id) throws ExamBusinessException {

    final Exam exam = examRepository.findOne(id);
    if (null == exam)
      throw new EmptyResultDataAccessException(1);
    return toExamDto(exam);
  }

  private ExamDto toExamDto(final Exam exam) throws ExamBusinessException {

    if (null == exam.getBatch()) {
      final CenterDto c = findByCenterId(exam.getCenter());
      return new ExamDto(exam, c);
    } else {
      final Long batchId = exam.getBatch();
      if (0 != batchId) {

        final BatchDto batch = getBatchById(batchId);
        return new ExamDto(exam, batch);
      } else {

        final CenterDto c = findByCenterId(exam.getCenter());
        return new ExamDto(exam, c);
      }
    }
  }

  @Transactional(
      readOnly = true)
  private CenterDto findByCenterId(final Integer id) {

    final Center center = centerRepository.findOne(id);
    return new CenterDto(center);
  }

  // called by deleteExam (Post /exam/{id}/delete)
  @Override
  public void delete(final Long id) throws ExamBusinessException {

    final Exam exam = examRepository.findOne(id);
    if (null != exam) {
      examRepository.delete(id);
      final List<Marks> marksList = marksRepository.findByExam(id);
      marksRepository.delete(marksList);
    } else
      throw new ExamBusinessException("Exam id " + id + "not found!");
  }

  @Override
  public Map<Long, BatchDto> getbatchMap() throws ExamBusinessException {

    final List<BatchDto> batches = getBatchData();
    final Map<Long, BatchDto> batchMap = new HashMap<>();
    batches.forEach(batch -> batchMap.put(batch.getId(), batch));
    return batchMap;
  }

  @Transactional(
      readOnly = true)
  private List<BatchDto> getBatchData() throws ExamBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final CenterDto centerDto = findByCenterId(center);
    final List<Batch> batchs = batchRepository.findByCenter(center);
    final List<BatchDto> list = new ArrayList<>();
    batchs.forEach(batch -> list.add(new BatchDto(batch, centerDto)));
    return list;
  }

  @Transactional(
      readOnly = true)
  private BatchDto getBatchById(@NotNull final Long id) throws ExamBusinessException {

    final Batch batch = batchRepository.findOne(id);
    if (null == batch)
      throw new EmptyResultDataAccessException(1);

    final CenterDto centerDto = findByCenterId(batch.getCenter());
    return new BatchDto(batch, centerDto);
  }

  @Override
  public ExamResultCard getResultPdf(final Long id) throws ExamBusinessException {

    final ExamResultCard examResultCard = new ExamResultCard();

    final ExamDto examDto = getExamById(id);
    if (null == examDto || examDto.getActiveId() != 1)
      return new ExamResultCard("Exam Not Found or InActive");

    if (null != examDto.getName())
      examResultCard.setName(examDto.getName());
    else
      examResultCard.setName("NA");
    if (null != examDto.getSubject())
      examResultCard.setSubject(examDto.getSubject());
    else
      examResultCard.setSubject("NA");
    if (null != examDto.getTotalMarks())
      examResultCard.setTotalMarks(String.valueOf(examDto.getTotalMarks()));
    else
      examResultCard.setTotalMarks("NA");
    if (null != examDto.getPassingMarks())
      examResultCard.setPassingMarks(String.valueOf(examDto.getPassingMarks()));
    else
      examResultCard.setPassingMarks("NA");
    if (null != examDto.getPreparedBy())
      examResultCard.setPreparedBy(examDto.getPreparedBy());
    else
      examResultCard.setPreparedBy("NA");
    if (null != examDto.getEvaluatedBy())
      examResultCard.setEvaluatedBy(examDto.getEvaluatedBy());
    else
      examResultCard.setEvaluatedBy("NA");
    if (null != examDto.getStrdtConduct())
      examResultCard.setStrdtConduct(examDto.getStrdtConduct());
    else
      examResultCard.setStrdtConduct("NA");
    if (null != examDto.getStrdtResult())
      examResultCard.setStrdtResult(examDto.getStrdtResult());
    else
      examResultCard.setStrdtResult("NA");

    if (null == examDto.getBatch() || null == examDto.getBatch().getId())
      return new ExamResultCard("Student is not assigned to any Batch");

    final BatchDto batchDto = getBatchById(examDto.getBatch().getId());
    if (null == batchDto || batchDto.getId() == null || batchDto.getActiveId() != 1)
      return new ExamResultCard("Batch Mismatch or Inactive");

    final Long batchId = examDto.getBatch().getId();

    if (null != batchDto.getCourseName())
      examResultCard.setCourseName(batchDto.getCourseName());
    else
      examResultCard.setCourseName("NA");

    if (null != batchDto.getSessionYear())
      examResultCard.setSessionYear(batchDto.getSessionYear());
    else
      examResultCard.setSessionYear("NA");

    if (null != batchDto.getStream())
      examResultCard.setStream(batchDto.getStream());
    else
      examResultCard.setStream("NA");

    if (null != batchDto.getSection())
      examResultCard.setSection(batchDto.getSection());
    else
      examResultCard.setSection("NA");

    final Integer centerId = securityService.findLoggedInUser().getCenter();
    final CenterDto centerDto = findByCenterId(centerId);
    if (null != centerDto && null != centerDto.getCenterName())
      examResultCard.setCenter(centerDto.getCenterName());
    else
      examResultCard.setCenter("NA");

    final List<ExamResultCardMarks> examResultCardMarks = new LinkedList<>();

    final List<Marks> markDtos = marksRepository.findByExamAndBatchAndCenterAndActive(id, batchId, centerId, 1);

    for (final Marks ed : markDtos) {

      final ExamResultCardMarks sr = new ExamResultCardMarks();

      if (null == ed.getMark())
        sr.setMarksObtained("AB");
      else
        sr.setMarksObtained(String.valueOf(ed.getMark()));

      final Student s = studentRepository.findOne(ed.getStudent());
      sr.setRollNumber(s.getRollNumber());
      sr.setStudentName(s.getName());

      examResultCardMarks.add(sr);
    }

    if (examResultCardMarks.isEmpty())
      return new ExamResultCard("No marks has been calculated for this exam.");
    else
      examResultCard.setExamResultCardMarks(examResultCardMarks);

    return examResultCard;
  }

}
