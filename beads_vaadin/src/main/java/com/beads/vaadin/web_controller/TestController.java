package com.beads.vaadin.web_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

  @GetMapping(value = "/admin")
  public String start() {
    return "admin_page";
  }
}
