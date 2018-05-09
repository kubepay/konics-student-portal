package com.kubepay.konics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.kubepay.konics.model.BatchDto;
import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.service.BatchService;
import com.kubepay.konics.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BatchController {

  private final SecurityService securityService;

  private final BatchService batchService;
  
  @Autowired
  public BatchController(final SecurityService securityService, final BatchService batchService) {

    this.securityService = securityService;
    this.batchService = batchService;
  }

  @GetMapping("/batch")
  public String getAllBatchesByCenter(final Model model) {

    model.addAttribute("highlight", "batch");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {
      
      model.addAttribute("batches", batchService.getBatchData());
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }
    return "batch/list";
  }

  @PostMapping("/batch")
  public String saveOrUpdateBatch(@ModelAttribute("batchForm") @Valid final BatchDto batch,
      final BindingResult result, final Model model,
      final RedirectAttributes redirectAttributes) {

    model.addAttribute("highlight", "batch");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    if (result.hasErrors()) {
      
      populateDefaultModel(model);
      return "batch/form";
    } else {
      
      Long id = null;
      try {
        
        id = batchService.saveOrUpdateBatch(batch);
        if (id != null) {
          
          redirectAttributes.addFlashAttribute("css", "success");
          redirectAttributes.addFlashAttribute("msg", "Batch saved successfully!");
        } else {
          
          redirectAttributes.addFlashAttribute("css", "danger");
          redirectAttributes.addFlashAttribute("msg", "Batch saving failed!");
        }
      } catch (final BusinessException e) {
        
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("css", "danger");
        redirectAttributes.addFlashAttribute("msg", e.getMessage());
      }
      if (id == null)
        return "redirect:/batch";
      else
        return "redirect:/batch/" + id;
    }
  }

  @RequestMapping(
      value = "/batch/add",
      method = RequestMethod.GET)
  public String showAddbatchForm(final Model model) {

    model.addAttribute("highlight", "batch");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    final BatchDto batch = new BatchDto();
    batch.setActiveId(1);
    model.addAttribute("batchForm", batch);
    populateDefaultModel(model);
    return "batch/form";
  }

  @RequestMapping(
      value = "/batch/{id}/update",
      method = RequestMethod.GET)
  public String showUpdateBatchForm(@PathVariable("id") final Long id, final Model model) {

    model.addAttribute("highlight", "batch");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {

      populateDefaultModel(model);
      final BatchDto batch = batchService.getBatchById(id);
      model.addAttribute("batchForm", batch);
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }
    return "batch/form";
  }

  @RequestMapping(
      value = "/batch/{id}/delete",
      method = RequestMethod.POST)
  public String deleteBatch(@PathVariable("id") @NotNull final Long id, final RedirectAttributes redirectAttributes) {
    
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "redirect:/login";

    try {
      
      batchService.delete(id);
      redirectAttributes.addFlashAttribute("css", "success");
      redirectAttributes.addFlashAttribute("msg", "Batch is deleted!");
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      redirectAttributes.addFlashAttribute("css", "danger");
      redirectAttributes.addFlashAttribute("msg", e.getMessage());
    }
    return "redirect:/batch";
  }

  @RequestMapping(
      value = "/batch/{id}",
      method = RequestMethod.GET)
  public String showBatch(@PathVariable("id") @NotNull final Long id, final Model model) {
    
    model.addAttribute("highlight", "batch");
    final SecureUserDto user = securityService.findLoggedInUser();
    if (null == user)
      return "login";
    model.addAttribute("user", user);

    try {
      
      final BatchDto batch = batchService.getBatchById(id);
      if (batch == null) {
        
        model.addAttribute("css", "danger");
        model.addAttribute("msg", "Batch not found");
      } else {
        
        model.addAttribute("batch", batch);
      }
    } catch (final BusinessException e) {
      
      log.error(e.getMessage(), e);
      model.addAttribute("css", "danger");
      model.addAttribute("msg", e.getMessage());
    }
    return "batch/show";
  }

  private void populateDefaultModel(Model model) {

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
    
    final List<String> sessionList = new ArrayList<String>();
    sessionList.add("2017-2018");
    sessionList.add("2017-2019");
    sessionList.add("2018-2019");
    sessionList.add("2018-2020");
    sessionList.add("2019-2020");
    sessionList.add("2019-2021");
    sessionList.add("2019-2020");
    sessionList.add("2019-2021");
    sessionList.add("2020-2021");
    sessionList.add("2020-2022");
    sessionList.add("2021-2022");
    sessionList.add("2021-2023");
    sessionList.add("2022-2023");
    sessionList.add("2022-2024");
    model.addAttribute("sessionList", sessionList);
    
  }
  
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ModelAndView handleEmptyData(final HttpServletRequest req, final Exception ex) {

    final ModelAndView model = new ModelAndView();
    model.setViewName("batch/show");
    model.addObject("msg", "Batch not found");
    return model;
  }

}
