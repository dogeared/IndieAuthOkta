package com.afitnerd.indieauth.IndieAuthOkta.controller;

import com.afitnerd.indieauth.IndieAuthOkta.service.PageScrapeService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class HomeController extends BaseController {

    private PageScrapeService pageScrapeService;

    public HomeController(PageScrapeService pageScrapeService) {
        this.pageScrapeService = pageScrapeService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/register-url")
    public ModelAndView registerUrl(@RequestParam String url) throws IOException {
        Assert.hasText(url, "url is required");
        String authorizationUrl = pageScrapeService.findAuthorizationTag(url);
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("authorizationUrl", authorizationUrl);
        return mav;
    }
}
