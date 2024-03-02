package com.jpa.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/view")
public class ViewController {

    @GetMapping(value = "/index")
    public ModelAndView view() {
        var view = new ModelAndView();
        view.setViewName("index");
        return view;
    }

}
