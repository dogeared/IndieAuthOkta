package com.afitnerd.indieauth.IndieAuthOkta.controller;

import com.afitnerd.indieauth.IndieAuthOkta.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
        IOException.class, IllegalArgumentException.class, RuntimeException.class
    })
    public ModelAndView error(Exception e) {
        ErrorResponse error = new ErrorResponse(e);
        log.error("Caught Exception: {}", e.getMessage(), e);

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", error);
        return mav;
    }
}
