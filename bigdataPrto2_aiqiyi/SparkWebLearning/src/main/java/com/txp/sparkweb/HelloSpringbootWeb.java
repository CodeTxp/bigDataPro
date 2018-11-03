package com.txp.sparkweb;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelloSpringbootWeb {

    @RequestMapping("/")
    public String page(){
        return "system/index";
    }

    @RequestMapping(value = "/first",method = RequestMethod.GET)
    public ModelAndView firstDemo(){
        return new ModelAndView("test");
    }
}
