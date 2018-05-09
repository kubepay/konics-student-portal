package com.kubepay.konics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kubepay.konics.entity.Batch;
import com.kubepay.konics.entity.Center;
import com.kubepay.konics.entity.Exam;
import com.kubepay.konics.entity.Marks;
import com.kubepay.konics.entity.Student;
import com.kubepay.konics.error.MarksBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.CenterDto;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.MarkDto;
import com.kubepay.konics.model.StudentDto;
import com.kubepay.konics.repository.BatchRepository;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.repository.ExamRepository;
import com.kubepay.konics.repository.MarksRepository;
import com.kubepay.konics.repository.StudentRepository;
import com.kubepay.konics.service.MarksService;
import com.kubepay.konics.service.SecurityService;

@Service("marksService")
public class MarksServiceImpl implements MarksService {

  private final StudentRepository studentRepository;

  private final BatchRepository batchRepository;

  private final CenterRepository centerRepository;

  private final SecurityService securityService;

  private final ExamRepository examRepository;

  private final MarksRepository marksRepository;

  @Autowired
  public MarksServiceImpl(final StudentRepository studentRepository,
      final ExamRepository examRepository,
      final MarksRepository marksRepository,
      final BatchRepository batchRepository,
      final CenterRepository centerRepository,
      final SecurityService securityService) {

    this.marksRepository = marksRepository;
    this.examRepository = examRepository;
    this.securityService = securityService;
    this.studentRepository = studentRepository;
    this.batchRepository = batchRepository;
    this.centerRepository = centerRepository;
  }

  @Override
  @Transactional(
      readOnly = true)
  public ExamDto getExamById(final Long id) throws MarksBusinessException {

    final Exam exam = examRepository.findOne(id);
    if (null == exam)
      throw new EmptyResultDataAccessException(1);
    return toExamDto(exam);
  }

  @Transactional(
      readOnly = true)
  private CenterDto findByCenterId(final Integer id) {

    final Center center = centerRepository.findOne(id);
    return new CenterDto(center);
  }

  @Transactional(
      readOnly = true)
  private BatchDto getBatchById(@NotNull final Long id) throws MarksBusinessException {

    final Batch batch = batchRepository.findOne(id);
    if (null == batch)
      throw new EmptyResultDataAccessException(1);

    final CenterDto centerDto = findByCenterId(batch.getCenter());
    return new BatchDto(batch, centerDto);
  }

  @Transactional(
      readOnly = true)
  private ExamDto toExamDto(final Exam exam) throws MarksBusinessException {

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

  @Override
  @Transactional
  public List<Long> saveOrUpdateMarks(final List<MarkDto> marks) throws MarksBusinessException {
    
    final List<Long> ids = new ArrayList<>();
    for(final MarkDto mark : marks) {
      final Marks mm = marksRepository.findOne(mark.getId());
      mm.setMark(mark.getMark());
      final Marks m = marksRepository.save(mm);
      ids.add(m.getId());
    }
    return ids;
  }



  private MarkDto toMarkDto(final Marks m) {

    final MarkDto dto = new MarkDto();
    dto.setId(m.getId());
    dto.setMark(m.getMark());
    dto.setActive(m.getActive());
    dto.setCenterId(m.getCenter());
    dto.setBatchId(m.getBatch());
    dto.setExamId(m.getExam());

    dto.setStudentId(m.getStudent());
    if (null != m.getStudent()) {
      final Student student = studentRepository.getOne(m.getStudent());
      if (null != student) {
        dto.setStudentName(student.getName());
        dto.setRollNumber(student.getRollNumber());
      }
    }
    return dto;
  }

  @Override
  public List<MarkDto> getMarksByExamId(final Long id) throws MarksBusinessException {

    final ExamDto exams = getExamById(id);
    final Integer centerId = securityService.findLoggedInUser().getCenter();

    final List<Marks> marks = marksRepository.findByExamAndBatchAndCenterAndActive(id, exams.getBatchId(), centerId, 1);

    final List<MarkDto> marksList = new ArrayList<>();

    for (final Marks m : marks) {
      marksList.add(toMarkDto(m));
    }

    return marksList;

  }
  
  @Override
  @Transactional
  public void generateTupleForExamId(final Long id) throws MarksBusinessException {
    final String user = securityService.findLoggedInUsername();
    final Integer centerId = securityService.findLoggedInUser().getCenter();
    final ExamDto exams = getExamById(id);
    if (null == exams)
      throw new MarksBusinessException("No Exam for this Id!");
    final Batch batch = batchRepository.findOne(exams.getBatchId());
    if (null == batch)
      throw new MarksBusinessException("No Batch for this Id!");
    final List<Student> students = studentRepository.findByBatchAndActive(batch.getId(), 1);
    if (students.isEmpty())
      throw new MarksBusinessException("No Students for this Id!");
    final Map<Long, Marks> studentMarksMap = getStudentMarksMap(id, exams.getBatchId(), centerId);
    for(Student s: students) {
      final Marks mm = studentMarksMap.get(s.getId());
      if (null == mm) {
        final Marks m = new Marks();
        m.setStudent(s.getId());
        m.setExam(id);
        m.setCenter(centerId);
        m.setBatch(exams.getBatchId());
        m.setActive(1);
        m.setCreatedBy(user);
        m.setCreatedAt(new Date());
        marksRepository.save(m);
      }
    }
    
  }
  
  private Map<Long, Marks> getStudentMarksMap(Long examId, Long batchId, Integer centerId){
    final List<Marks> marks = marksRepository.findByExamAndBatchAndCenterAndActive(examId, batchId, centerId, 1);
    final Map<Long, Marks> map = new HashMap<>();
    for(Marks m: marks) {
      map.put(m.getStudent(), m);
    }
    return map;
    
  }
  

  // final Exam exam = examRepository.findOne(id);
  // final List<StudentDto> studentList = studentService.findByBatch(exam.getBatch());
  // final List<Marks> marksList = marksRepository.findByExam(id);
  // final Map<Long, Marks> marksMap = prepareMarksMap(marksList);
  // final List<MarkDto> marksDtos = new ArrayList<>();
  // for (final StudentDto stu : studentList) {
  // Marks marks = marksMap.get(stu.getId());
  // if (marks == null) {
  // // insert marks
  // // add new marks to map
  // // create marksdto
  // // add to marksdto List
  // } else {
  // // create marksdto
  // //// add to marksdto List
  // }
  // }
  // return marksDtos;

  @Transactional(
      readOnly = true)
  public List<StudentDto> getStudentData() throws MarksBusinessException {

    final Integer center = securityService.findLoggedInUser().getCenter();
    final List<Batch> batches = batchRepository.findByCenter(center);
    final Map<Long, BatchDto> batchMap = prepareBatchMap(batches);

    final List<Student> students = new ArrayList<>();
    for (final Batch batch : batches) {
      students.addAll(studentRepository.findByBatch(batch.getId()));
    }

    final List<StudentDto> list = new ArrayList<>();
    students.forEach(s -> list.add(toStudentDto(s, batchMap)));
    return list;
  }

  private StudentDto toStudentDto(final Student student, final Map<Long, BatchDto> batchMap) {

    final Long key = student.getBatch();
    final BatchDto batch = batchMap.get(key);
    return new StudentDto(student, batch);
  }

  private Map<Long, BatchDto> prepareBatchMap(final List<Batch> batches) {

    final Map<Long, BatchDto> map = new HashMap<>();
    for (final Batch batch : batches) {
      final BatchDto dto = toBatchDto(batch);
      map.put(batch.getId(), dto);
    }
    return map;
  }

  private BatchDto toBatchDto(final Batch batch) {

    final Center c = centerRepository.findOne(batch.getCenter());
    return new BatchDto(batch, new CenterDto(c));
  }


}
