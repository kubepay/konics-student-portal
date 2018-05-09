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
import com.kubepay.konics.error.StudentBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.CenterDto;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.StudentDto;
import com.kubepay.konics.model.StudentReportCard;
import com.kubepay.konics.model.StudentReportCardMarks;
import com.kubepay.konics.repository.BatchRepository;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.repository.ExamRepository;
import com.kubepay.konics.repository.MarksRepository;
import com.kubepay.konics.repository.StudentRepository;
import com.kubepay.konics.service.SecurityService;
import com.kubepay.konics.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  private final MarksRepository marksRepository;

  private final CenterRepository centerRepository;

  private final ExamRepository examRepository;

  private final BatchRepository batchRepository;

  private final SecurityService securityService;

  @Autowired
  public StudentServiceImpl(final StudentRepository studentRepository,
      final BatchRepository batchRepository,
      final CenterRepository centerRepository,
      final ExamRepository examRepository,
      final MarksRepository marksRepository,
      final SecurityService securityService) {

    this.securityService = securityService;
    this.marksRepository = marksRepository;
    this.examRepository = examRepository;
    this.studentRepository = studentRepository;
    this.centerRepository = centerRepository;
    this.batchRepository = batchRepository;
  }

  // called by getAllStudentsByCenter (GET /student)
  @Transactional(
      readOnly = true)
  @Override
  public List<StudentDto> getStudentData() throws StudentBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final List<Student> students = studentRepository.findByCenter(center);
    final List<StudentDto> list = new ArrayList<>();

    final Map<Long, BatchDto> batchMap = getbatchMap();
    students.forEach(student -> {
      final Long key = student.getBatch();
      final BatchDto batch = batchMap.get(key);
      list.add(new StudentDto(student, batch));
    });

    return list;
  }

  // called by populateDefaultModel in StudentController
  @Transactional(
      readOnly = true)
  @Override
  public Map<Long, BatchDto> getbatchMap() throws StudentBusinessException {

    final Map<Long, BatchDto> map = new HashMap<>();
    final List<BatchDto> batches = getBatchData();
    batches.forEach(batch -> {
      map.put(batch.getId(), batch);
    });
    return map;
  }

  private List<BatchDto> getBatchData() throws StudentBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final CenterDto centerDto = findByCenterId(center);
    final List<Batch> batchs = batchRepository.findByCenter(center);
    final List<BatchDto> list = new ArrayList<>();
    batchs.forEach(batch -> list.add(new BatchDto(batch, centerDto)));
    return list;
  }

  @Transactional(
      readOnly = true)
  private CenterDto findByCenterId(final Integer id) {

    final Center center = centerRepository.findOne(id);
    return new CenterDto(center);
  }

  // called by saveOrUpdateStudent (POST /student)
  @Override
  @Transactional
  public Long saveOrUpdateStudent(@NotNull final StudentDto studentDto) throws StudentBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final String user = securityService.findLoggedInUsername();
    final Long id = studentDto.getId();
    if (null == id) {

      final Student student = new Student();
      fillStudent(student, studentDto, center, user, false);
      final Student updatedstudent = studentRepository.save(student);
      return updatedstudent.getId();
    } else {

      final Student student = studentRepository.findOne(id);
      if (null == student)
        throw new EmptyResultDataAccessException(1);

      fillStudent(student, studentDto, center, user, true);
      final Student updatedstudent = studentRepository.save(student);
      return updatedstudent.getId();
    }
  }

  private void fillStudent(final Student student, final StudentDto studentDto, final Integer center, final String user,
      final boolean update) {

    student.setAddress1(cleanStr(studentDto.getAddress1()));
    student.setAddress2(cleanStr(studentDto.getAddress2()));
    student.setAddress3(cleanStr(studentDto.getAddress3()));
    student.setCategory(cleanStr(studentDto.getCategory()));
    student.setEmail(cleanStr(studentDto.getEmail()));
    student.setFatherName(cleanStr(studentDto.getFatherName()));
    student.setGender(cleanStr(studentDto.getGender()));
    student.setGOccupation(cleanStr(studentDto.getOccupation()));
    student.setMobile(cleanStr(studentDto.getMobile()));
    student.setMotherName(cleanStr(studentDto.getMotherName()));
    student.setName(cleanStr(studentDto.getName()));
    student.setOtherPhone(cleanStr(studentDto.getOtherPhone()));
    student.setPercentage(cleanStr(studentDto.getPercentage()));
    student.setPhone(cleanStr(studentDto.getPhone()));
    student.setRegNo(cleanStr(studentDto.getRegno()));
    student.setRemarks(cleanStr(studentDto.getRemarks()));
    student.setRollNumber(cleanStr(studentDto.getRollNumber()));
    student.setSchoolName(cleanStr(studentDto.getSchoolName()));
    student.setAttendence(studentDto.getAttendance());
    student.setDateOfBirth(toDateFromStr(studentDto.getStrdtBirth()));
    student.setDateOfJoining(toDateFromStr(studentDto.getStrdtJoining()));
    student.setDateOfLeaving(toDateFromStr(studentDto.getStrdtLeaving()));
    student.setActive(studentDto.getActiveId());

    if (null != studentDto.getBatchId() && 0 != studentDto.getBatchId())
      student.setBatch(studentDto.getBatchId());
    else
      student.setBatch(null);
    if (StringUtils.isNotBlank(studentDto.getBloodGroup())
        && !StringUtils.equals("NONE", studentDto.getBloodGroup()))
      student.setBloodGroup(studentDto.getBloodGroup());
    else
      student.setBloodGroup(null);
    if (StringUtils.isNotBlank(studentDto.getState())
        && !StringUtils.equals("NONE", studentDto.getState()))
      student.setState(studentDto.getState());
    else
      student.setState(null);

    student.setCenter(center);

    if (update) {
      student.setModifiedBy(user);
      student.setModifiedAt(new Date());
    } else {
      student.setCreatedBy(user);
      student.setCreatedAt(new Date());
    }
  }

  private String cleanStr(String str) {

    if (StringUtils.isNotBlank(str))
      return StringUtils.trim(str);
    else
      return null;
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

  // called by showUpdateBatchForm (GET /student/{id}/update)
  // called by showStudentById (GET /student/{id})
  @Override
  @Transactional(
      readOnly = true)
  public StudentDto getStudentById(@NotNull final Long id) throws StudentBusinessException {

    final Student student = studentRepository.findOne(id);
    if (null == student)
      throw new EmptyResultDataAccessException(1);
    if (null == student.getBatch() || 0 == student.getBatch()) {

      final CenterDto c = findByCenterId(student.getCenter());
      return new StudentDto(student, c);
    }

    final BatchDto batch = getBatchById(student.getBatch());
    return new StudentDto(student, batch);

  }

  // called by deleteStudent (POST /student/{id}/delete)
  @Override
  @Transactional
  public void delete(@NotNull final Long id) throws StudentBusinessException {

    final Student student = studentRepository.findOne(id);
    if (null == student)
      throw new StudentBusinessException("Student id " + id + "not found!");

    deleteMarksForStudent(id);
    studentRepository.delete(id);
  }

  @Transactional
  private void deleteMarksForStudent(Long id) {

    final List<Marks> marks = marksRepository.findByStudent(id);
    marksRepository.delete(marks);
  }

  @Override
  public StudentReportCard getReportCardPdf(@NotNull final Long id) throws StudentBusinessException {

    final StudentReportCard studentReportCard = new StudentReportCard();

    final StudentDto studentDto = getStudentById(id);
    if (null == studentDto || studentDto.getActiveId() != 1)
      return new StudentReportCard("Student Not Found or InActive");

    if (null != studentDto.getRollNumber())
      studentReportCard.setRollNumber(studentDto.getRollNumber());
    else
      studentReportCard.setRollNumber("NA");

    if (null != studentDto.getName())
      studentReportCard.setName(studentDto.getName());
    else
      studentReportCard.setName("NA");

    if (null != studentDto.getAttendance())
      studentReportCard.setStudentAttendance(String.valueOf(studentDto.getAttendance()));
    else
      studentReportCard.setStudentAttendance("NA");

    if (null == studentDto.getBatch() || null == studentDto.getBatch().getId())
      return new StudentReportCard("Student is not assigned to any Batch");

    final BatchDto batchDto = getBatchById(studentDto.getBatch().getId());
    if (null == batchDto || batchDto.getId() == null || batchDto.getActiveId() != 1)
      return new StudentReportCard("Batch Mismatch or Inactive");

    final Long batchId = studentDto.getBatch().getId();

    if (null != batchDto.getAttendance())
      studentReportCard.setBatchAttendance(String.valueOf(batchDto.getAttendance()));
    else
      studentReportCard.setBatchAttendance("NA");

    if (null != batchDto.getCourseName())
      studentReportCard.setCourseName(batchDto.getCourseName());
    else
      studentReportCard.setCourseName("NA");

    if (null != batchDto.getSessionYear())
      studentReportCard.setSessionYear(batchDto.getSessionYear());
    else
      studentReportCard.setSessionYear("NA");

    if (null != batchDto.getStream())
      studentReportCard.setStream(batchDto.getStream());
    else
      studentReportCard.setStream("NA");

    if (null != batchDto.getSection())
      studentReportCard.setSection(batchDto.getSection());
    else
      studentReportCard.setSection("NA");

    final Integer centerId = securityService.findLoggedInUser().getCenter();
    final CenterDto centerDto = findByCenterId(centerId);
    if (null != centerDto && null != centerDto.getCenterName())
      studentReportCard.setCenter(centerDto.getCenterName());
    else
      studentReportCard.setCenter("NA");

    // id, batchId, centerId,
    final List<StudentReportCardMarks> studentReportCardMarks = new LinkedList<>();

    final List<ExamDto> examDtos = getExamsByBatch(batchId);

    for (final ExamDto ed : examDtos) {

      final StudentReportCardMarks sr = new StudentReportCardMarks();
      final List<Marks> marks = marksRepository.findByStudentAndExamAndBatchAndCenterAndActive(id, ed.getId(), batchId,
          centerId, 1);
      if (marks.isEmpty())
        sr.setMarksObtained("AB");
      else if (marks.size() == 1) {
        Marks m = marks.get(0);
        if (null == m.getMark())
          sr.setMarksObtained("AB");
        else
          sr.setMarksObtained(String.valueOf(m.getMark()));
      } else {
        boolean allNull = true;
        int tot = 0;

        for (final Marks mks : marks) {
          if (null != mks.getMark()) {
            allNull = false;
            tot += mks.getMark();
          }
        }

        if (allNull)
          sr.setMarksObtained("AB");
        else
          sr.setMarksObtained(String.valueOf(tot));
      }
      sr.setSubject(ed.getSubject());
      sr.setTestName(ed.getName());
      if (null != ed.getTotalMarks())
        sr.setTotalMarks(String.valueOf(ed.getTotalMarks()));
      studentReportCardMarks.add(sr);
    }

    if (studentReportCardMarks.isEmpty())
      return new StudentReportCard("Student has not attedned any exam");
    else
      studentReportCard.setStudentReportCardMarks(studentReportCardMarks);
    
    return studentReportCard;
  }

//  private MarkDto toMarksDto(Marks mark) {
//
//    final MarkDto markDto = new MarkDto();
//    if (null != mark.getStudent()) {
//      final Student student = studentRepository.findOne(mark.getStudent());
//      if (null != student) {
//        markDto.setRollNumber(student.getRollNumber());
//        markDto.setStudentName(student.getName());
//      }
//    }
//    markDto.setActive(mark.getActive());
//    markDto.setId(mark.getId());
//    markDto.setMark(mark.getMark());
//    markDto.setStudentId(mark.getStudent());
//
//    markDto.setCenterId(mark.getCenter());
//    markDto.setBatchId(mark.getBatch());
//    markDto.setExamId(mark.getExam());
//    return null;
//  }

  private List<ExamDto> getExamsByBatch(Long id) throws StudentBusinessException {

    final List<Exam> exams = examRepository.findByBatchAndActive(id, 1);
    final List<ExamDto> examDtos = new ArrayList<>();
    for (final Exam exam : exams) {
      examDtos.add(toExamDto(exam));
    }
    return examDtos;
  }

  private ExamDto toExamDto(final Exam exam) throws StudentBusinessException {

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
  private BatchDto getBatchById(@NotNull final Long id) throws StudentBusinessException {

    final Batch batch = batchRepository.findOne(id);
    if (null == batch)
      throw new EmptyResultDataAccessException(1);

    final CenterDto centerDto = findByCenterId(batch.getCenter());
    return new BatchDto(batch, centerDto);
  }

}
