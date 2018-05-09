package com.kubepay.konics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kubepay.konics.error.BusinessException;
import com.kubepay.konics.error.ExamBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.service.ExamService;
import com.kubepay.konics.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ExamController {

  private final SecurityService securityService;

  private final ExamService examService;

  @Autowired
  public ExamController(final SecurityService securityService, 
      final ExamService examService) {

    this.securityService = securityService;
    this.examService = examService;
  }

  @GetMapping("/exam")
  public String getAllExams(final Model model) {

    model.addAttribute("highlight", "exam");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";

    model.addAttribute("user", user);

    try {
      model.addAttribute("exams", examService.getExamData());
    } catch (final BusinessException e) {
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "exam/list";

  }

  @PostMapping("/exam")
  public String saveOrUpdateExam(@ModelAttribute("examForm") @Valid final ExamDto exam,
      final BindingResult result, final Model model,
      final RedirectAttributes redirectAttributes) {

    model.addAttribute("highlight", "exam");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    if (result.hasErrors()) {

      populateDefaultModel(model);
      return "exam/form";
    } else {
      
      Long id = null;
      try {

        id = examService.saveOrUpdateExam(exam);
        redirectAttributes.addFlashAttribute("css", "success");
        if (id != null) {
          redirectAttributes.addFlashAttribute("msg", "Exam saved successfully!");
        } else {
          redirectAttributes.addFlashAttribute("msg", "Exam saving failed!");
        }
      } catch (final BusinessException e) {

        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("css", "danger");
        redirectAttributes.addFlashAttribute("msg", e.getMessage());
      }

      if (id == null)
        return "redirect:/exam";
      else
        return "redirect:/exam/" + id;
    }
  }

  @RequestMapping(
      value = "/exam/add",
      method = RequestMethod.GET)
  public String showAddExamForm(final Model model) {

    model.addAttribute("highlight", "exam");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    final ExamDto exam = new ExamDto();
    exam.setActiveId(1);
    model.addAttribute("examForm", exam);
    populateDefaultModel(model);

    return "exam/form";
  }

  @RequestMapping(
      value = "/exam/{id}/update",
      method = RequestMethod.GET)
  public String showUpdateExamForm(@PathVariable("id") final Long id, final Model model) {

    model.addAttribute("highlight", "exam");
    final SecureUserDto user = securityService.findLoggedInUser();

    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {

      populateDefaultModel(model);
      final ExamDto exam = examService.getExamById(id);
      model.addAttribute("examForm", exam);
    } catch (final BusinessException e) {

      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "exam/form";
  }

  @RequestMapping(
      value = "/exam/{id}/delete",
      method = RequestMethod.POST)
  public String deleteExam(@PathVariable("id") @NotNull final Long id, final RedirectAttributes redirectAttributes) {

    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "redirect:/login";

    try {

      examService.delete(id);

      redirectAttributes.addFlashAttribute("css", "success");
      redirectAttributes.addFlashAttribute("msg", "Student is deleted!");
    } catch (final BusinessException e) {

      log.error(e.getMessage(), e);
      redirectAttributes.addFlashAttribute("css", "danger");
      redirectAttributes.addFlashAttribute("msg", e.getMessage());
    }

    return "redirect:/exam";
  }

  @RequestMapping(
      value = "/exam/{id}",
      method = RequestMethod.GET)
  public String showExam(@PathVariable("id") @NotNull final Long id, final Model model) {

    model.addAttribute("highlight", "exam");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {

      final ExamDto exam = examService.getExamById(id);

      if (exam == null) {
        model.addAttribute("css", "danger");
        model.addAttribute("msg", "Student not found");
      } else {
        model.addAttribute("exam", exam);
      }
    } catch (final BusinessException e) {

      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "exam/show";
  }

  private void populateDefaultModel(Model model) {

    final List<String> subjectList = new ArrayList<String>();
    subjectList.add("PHYSICS");
    subjectList.add("CHEMISTRY");
    subjectList.add("BIOLOGY");
    subjectList.add("MATHEMATICS");
    subjectList.add("ENGLISH");
    subjectList.add("SOCIAL SCIENCE");
    subjectList.add("MENTAL ABILITY");
    model.addAttribute("subjectList", subjectList);

    final List<Integer> datesList = new ArrayList<>();
    for (int d = 1; d < 32; d++)
      datesList.add(d);
    model.addAttribute("datesList", datesList);

    final Map<Integer, String> monthList = new LinkedHashMap<Integer, String>();
    monthList.put(1, "January");
    monthList.put(2, "February");
    monthList.put(3, "March");
    monthList.put(4, "April");
    monthList.put(5, "May");
    monthList.put(6, "June");
    monthList.put(7, "July");
    monthList.put(8, "August");
    monthList.put(9, "September");
    monthList.put(10, "October");
    monthList.put(11, "November");
    monthList.put(12, "December");
    model.addAttribute("monthList", monthList);

    final List<Integer> yearsList = new ArrayList<>();
    for (int y = 2017; y < 2024; y++)
      yearsList.add(y);
    model.addAttribute("yearsList", yearsList);
    
    final Map<Long, String> getBatchMap = new HashMap<Long, String>();
    try {
      final Map<Long, BatchDto> batchMap = examService.getbatchMap();
      
      for(Long key : batchMap.keySet()) {
        final BatchDto value = batchMap.get(key);
        final String [] attr = new String []{
            value.getCourseName(), value.getSessionYear(), value.getSection()};
        getBatchMap.put(key, StringUtils.join(attr, " "));
      }
    }catch (final ExamBusinessException e) {
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }
    model.addAttribute("batchList", getBatchMap);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ModelAndView handleEmptyData(final HttpServletRequest req, final Exception ex) {

    final ModelAndView model = new ModelAndView();
    model.setViewName("exam/show");
    model.addObject("msg", "Exam not found");
    return model;

  }
  

}
