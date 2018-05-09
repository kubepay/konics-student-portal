package com.kubepay.konics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kubepay.konics.service.SecurityService;

@Controller
public class DefaultController {

  @Autowired
  private SecurityService securityService;

  @RequestMapping(
      value = { "/" },
      method = RequestMethod.GET)
  public String index(final Model model) {

    if (null != securityService.findLoggedInUser()) {
      model.addAttribute("user", securityService.findLoggedInUser());
      model.addAttribute("highlight", "welcome");
      return "welcome";
    } else
      return "login";
  }

  @GetMapping("/welcome")
  public String welcome(final Model model) {

    if (null != securityService.findLoggedInUser()) {
      model.addAttribute("user", securityService.findLoggedInUser());
      model.addAttribute("highlight", "welcome");
      return "welcome";
    } else
      return "login";
  }

  //Depreciate
  @GetMapping("/admin")
  public String admin(final Model model) {

    if (null != securityService.findLoggedInUser()) {
      model.addAttribute("user", securityService.findLoggedInUser());
      model.addAttribute("highlight", "admin");
      return "admin";
    } else
      return "login";
  }

  @GetMapping("/403")
  public String error403(final Model model) {

    return "/error/403";
  }

}
