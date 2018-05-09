package com.kubepay.konics.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kubepay.konics.entity.User;
import com.kubepay.konics.service.SecurityService;
import com.kubepay.konics.service.UserService;
import com.kubepay.konics.validator.UserValidator;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserValidator userValidator;

  //Depreciate
  @RequestMapping(
      value = "/registration",
      method = RequestMethod.GET)
  public ModelAndView registration() {
    final ModelAndView model = new ModelAndView("registration");
    model.addObject("userForm", new User());
    return model;
  }

  //Depreciate
  @RequestMapping(
      value = "/registration",
      method = RequestMethod.POST)
  public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

    userValidator.validate(userForm, bindingResult);

    if (bindingResult.hasErrors()) {
      return "registration";
    }

    userService.saveUser(userForm);
    return "redirect:/welcome";
  }

  @RequestMapping(
      value = "/login",
      method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {

    if (error != null)
      model.addAttribute("error", "Your username and password is invalid.");

    if (logout != null)
      model.addAttribute("message", "You have been logged out successfully.");

    if (null != securityService.findLoggedInUsername())
      return "welcome";

    return "login";
  }
  
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ModelAndView handleEmptyData(final HttpServletRequest req, final Exception ex) {

    final ModelAndView model = new ModelAndView();
    model.setViewName("student/show");
    model.addObject("msg", "Student not found");
    return model;

  }

}
