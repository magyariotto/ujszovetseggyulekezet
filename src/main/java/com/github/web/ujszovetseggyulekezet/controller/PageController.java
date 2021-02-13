package com.github.web.ujszovetseggyulekezet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PageController {
    @GetMapping("/")
    public String rest(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelIndex = new ModelAndView("index");
        return modelIndex;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/create_account")
    public String createAccount() {
        return "create_account";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
