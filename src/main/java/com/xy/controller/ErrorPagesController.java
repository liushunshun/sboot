/*
package com.xy.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorPagesController implements ErrorController {
    @RequestMapping("/404")
    public ModelAndView notFound() {
        return new ModelAndView("404");
    }

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        return new ModelAndView("404");
    }
    @RequestMapping("/405")
    public ModelAndView methodNotAllow() {
        return new ModelAndView("404");
    }

    @RequestMapping("/500")
    public ModelAndView internalServerError() {
        return new ModelAndView("500");
    }


    private static final String PATH = "/error";
    @RequestMapping(value = PATH)
    public ModelAndView error() {
        return new ModelAndView("500");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
*/
