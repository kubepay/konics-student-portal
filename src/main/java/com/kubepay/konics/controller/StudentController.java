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
import com.kubepay.konics.error.StudentBusinessException;
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.model.StudentDto;
import com.kubepay.konics.model.StudentReportCard;
import com.kubepay.konics.service.SecurityService;
import com.kubepay.konics.service.StudentService;
import com.kubepay.konics.view.StudentReportCardPdfView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StudentController {

  private final SecurityService securityService;

  private final StudentService studentService;
  
  @Autowired
  public StudentController(final SecurityService securityService, 
      final StudentService studentService) {

    this.securityService = securityService;
    this.studentService = studentService;
  }

  @GetMapping("/student")
  public String getAllStudentsByCenter(final Model model) {

    model.addAttribute("highlight", "student");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";

    model.addAttribute("user", user);

    try {
      
      model.addAttribute("students", studentService.getStudentData());
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "student/list";
  }

  @PostMapping("/student")
  public String saveOrUpdateStudent(@ModelAttribute("studentForm") @Valid final StudentDto student,
      final BindingResult result, final Model model,
      final RedirectAttributes redirectAttributes) throws BusinessException {

    model.addAttribute("highlight", "student");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    if (result.hasErrors()) {
      
      populateDefaultModel(model);
      return "student/form";
    } else {
      
      Long id = null;
      try {
        
        id = studentService.saveOrUpdateStudent(student);
        redirectAttributes.addFlashAttribute("css", "success");
        if (id != null) {
          redirectAttributes.addFlashAttribute("msg", "Student saved successfully!");
        } else {
          redirectAttributes.addFlashAttribute("msg", "Student saving failed!");
        }
      } catch (final BusinessException e) {
        
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("css", "danger");
        redirectAttributes.addFlashAttribute("msg", e.getMessage());
      }
      if (id == null)
        return "redirect:/student";
      else
        return "redirect:/student/" + id;
    }
  }
  
  @RequestMapping(
      value = "/student/add",
      method = RequestMethod.GET)
  public String showAddbatchForm(final Model model) throws BusinessException {

    model.addAttribute("highlight", "student");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    final StudentDto student = new StudentDto();
    student.setActiveId(1);
    model.addAttribute("studentForm", student);
    populateDefaultModel(model);

    return "student/form";
  }

  @RequestMapping(
      value = "/student/{id}/update",
      method = RequestMethod.GET)
  public String showUpdateBatchForm(@PathVariable("id") final Long id, final Model model) {

    model.addAttribute("highlight", "student");
    final SecureUserDto user = securityService.findLoggedInUser();
    
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {

      populateDefaultModel(model);
      final StudentDto student = studentService.getStudentById(id);
      model.addAttribute("studentForm", student);
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "student/form";
  }

  @RequestMapping(
      value = "/student/{id}/delete",
      method = RequestMethod.POST)
  public String deleteStudent(@PathVariable("id") @NotNull final Long id, final RedirectAttributes redirectAttributes) {
    
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "redirect:/login";

    try {
      
      studentService.delete(id);
      redirectAttributes.addFlashAttribute("css", "success");
      redirectAttributes.addFlashAttribute("msg", "Student is deleted!");
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      redirectAttributes.addFlashAttribute("css", "danger");
      redirectAttributes.addFlashAttribute("msg", e.getMessage());
    }
    
    return "redirect:/student";
  }

  @RequestMapping(
      value = "/student/{id}",
      method = RequestMethod.GET)
  public String showStudentById(@PathVariable("id") @NotNull final Long id, final Model model) {
    
    model.addAttribute("highlight", "student");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {
      
      final StudentDto student = studentService.getStudentById(id);

      if (student == null) {
        model.addAttribute("css", "danger");
        model.addAttribute("msg", "Student not found");
      } else {
        model.addAttribute("student", student);
      }
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }

    return "student/show";
  }

  private void populateDefaultModel(Model model) throws StudentBusinessException {

    final List<String> sectionList = new ArrayList<String>();
    sectionList.add("Weekend");
    sectionList.add("Weekdays");
    sectionList.add("Regular");
    model.addAttribute("sectionList", sectionList);
    
    final List<String> streamList = new ArrayList<String>();
    streamList.add("Foundation");
    streamList.add("Medical");
    streamList.add("Engineering");
    streamList.add("NEET/AIIMS");
    streamList.add("JEE Main & Adv.");
    model.addAttribute("streamList", streamList);
    
    final List<Integer> datesList = new ArrayList<>();
    for(int d = 1; d < 32; d++) 
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
    for(int y = 1980; y < 2018; y++) 
      yearsList.add(y);  
    model.addAttribute("yearsList", yearsList);
    
    final List<String> statesList = new ArrayList<>();
    statesList.add("Andaman and Nicobar Islands");
    statesList.add("Andhra Pradesh");
    statesList.add("Arunachal Pradesh");  
    statesList.add("Assam");
    statesList.add("Bihar");
    statesList.add("Chandigarh");
    statesList.add("Chhattisgarh");
    statesList.add("Dadra and Nagar Haveli");
    statesList.add("Daman and Diu");
    statesList.add("Goa");
    statesList.add("Gujarat");
    statesList.add("Haryana");
    statesList.add("Himachal Pradesh");
    statesList.add("Jammu and Kashmir");
    statesList.add("Jharkhand");
    statesList.add("Karnataka");
    statesList.add("Kerala");
    statesList.add("Lakshadweep");
    statesList.add("Madhya Pradesh");
    statesList.add("Maharashtra");
    statesList.add("Manipur");
    statesList.add("Meghalaya");
    statesList.add("Mizoram");
    statesList.add("Nagaland");
    statesList.add("New Delhi");
    statesList.add("Odisha");
    statesList.add("Puducherry");
    statesList.add("Punjab");
    statesList.add("Rajasthan");
    statesList.add("Sikkim");
    statesList.add("Tamil Nadu");
    statesList.add("Telangana");
    statesList.add("Tripura");
    statesList.add("Uttar Pradesh");
    statesList.add("Uttarakhand");
    statesList.add("West Bengal");
    model.addAttribute("statesList", statesList);
    
    final List<String> gendersList = new ArrayList<>();
    gendersList.add("Male");
    gendersList.add("Female");
    gendersList.add("Transgender");
    model.addAttribute("gendersList", gendersList);
    
    final List<String> bloodGroupsList = new ArrayList<>();
    bloodGroupsList.add("A");
    bloodGroupsList.add("O");
    bloodGroupsList.add("B");
    bloodGroupsList.add("AB");
    bloodGroupsList.add("A+");
    bloodGroupsList.add("O+");
    bloodGroupsList.add("B+");
    bloodGroupsList.add("AB+");
    bloodGroupsList.add("A-");
    bloodGroupsList.add("O-");
    bloodGroupsList.add("B-");
    bloodGroupsList.add("AB-");
    model.addAttribute("bloodGroupsList", bloodGroupsList);
    
    final Map<Long, String> batchMap = new HashMap<>();
    final Map<Long, BatchDto> batchDtoMap = studentService.getbatchMap();
    
    for(final Long key: batchDtoMap.keySet()) {
      final BatchDto value = batchDtoMap.get(key);
      final String [] attr = {value.getCourseName(), value.getSessionYear(), value.getSection()};
      batchMap.put(key, StringUtils.join(attr, " "));
    }  
    model.addAttribute("batchList", batchMap);
  }
  
  @RequestMapping(
      value = "/student/{id}/reportcard/pdf",
      method = RequestMethod.GET)
  public ModelAndView showReportCardPdf(@PathVariable("id") final Long id) throws Exception {
    Map<String, Object> model = new HashMap<>();

    final StudentReportCard studentReportCard = studentService.getReportCardPdf(id);
    model.put("studentReportCard", studentReportCard);

    return new ModelAndView(new StudentReportCardPdfView(), model);
  }
  
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ModelAndView handleEmptyData(final HttpServletRequest req, final Exception ex) {

    final ModelAndView model = new ModelAndView();
    model.setViewName("student/show");
    model.addObject("msg", "Student not found");
    return model;

  }

}
