package com.kubepay.konics.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kubepay.konics.error.MarksBusinessException;
import com.kubepay.konics.model.ExamDto;
import com.kubepay.konics.model.MarkDto;
import com.kubepay.konics.model.MarksForm;
import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.service.MarksService;
import com.kubepay.konics.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MarksController {
  
  private final SecurityService securityService;

  private final MarksService marksService;
  
  @Autowired
  public MarksController(final SecurityService securityService, final MarksService marksService) {

    this.securityService = securityService;
    this.marksService = marksService;
  }
  

 @RequestMapping(
     value = "/exam/{id}/marks",
     method = RequestMethod.GET)
 public String marks(@PathVariable("id") @NotNull final Long id, final Model model) {

   model.addAttribute("highlight", "exam");
   final SecureUserDto user = securityService.findLoggedInUser();
   if (null == user)
     return "login";
   model.addAttribute("user", user);
   try {
     final ExamDto exam = marksService.getExamById(id);
     if (exam == null) {
       model.addAttribute("css", "danger");
       model.addAttribute("msg", "Exam not found");
     } else {
       marksService.generateTupleForExamId(id);
       final List<MarkDto> marks = marksService.getMarksByExamId(id);
       model.addAttribute("marks", marks);
       model.addAttribute("exam", exam);
       model.addAttribute("id", id);
     }
   } catch (final MarksBusinessException e) {

     log.error(e.getMessage(), e);
     model.addAttribute("css", "danger");
     model.addAttribute("msg", e.getMessage());
   }

   return "exam/marks";
 }
 
 @RequestMapping(value = "/marks", method = RequestMethod.POST)
 public String save(@ModelAttribute("marksForm") MarksForm marksForm, final BindingResult result, final Model model,
     final RedirectAttributes redirectAttributes) {
   
   final List<MarkDto> marks = marksForm.getMarks();
   
   if(null != marks && marks.size() > 0) {
     try {
       marksService.saveOrUpdateMarks(marks);
       redirectAttributes.addFlashAttribute("msg", "Marks saved successfully!");
    } catch (final Exception e) {
      
      log.error(e.getMessage(), e);
      redirectAttributes.addFlashAttribute("css", "danger");
      redirectAttributes.addFlashAttribute("msg", e.getMessage());
    }
   }
   return "redirect:/exam";
 }

}
