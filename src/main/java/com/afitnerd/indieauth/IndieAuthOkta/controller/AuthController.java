package com.afitnerd.indieauth.IndieAuthOkta.controller;

import com.afitnerd.indieauth.IndieAuthOkta.model.AuthResponse;
import com.afitnerd.indieauth.IndieAuthOkta.model.GetAuthRequest;
import com.afitnerd.indieauth.IndieAuthOkta.model.PostAuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {

    @Value("#{ @environment['authorization.redirect-uri'] }")
    private String redirectUri;


    @GetMapping("/auth")
    public void auth(GetAuthRequest request, HttpServletResponse response) throws IOException {
        if (!redirectUri.equals(request.getRedirectUri())) {
            throw new RuntimeException("Redirect Uri: " + request.getRedirectUri() + " is not allowed.");
        }
        response.sendRedirect(redirectUri + "?state=" + request.getState() + "&code=" + "codeocdeocode");
    }

    @PostMapping("/auth")
    public @ResponseBody
    AuthResponse auth(@ModelAttribute PostAuthRequest request) {

        // exchange code for id token

        return new AuthResponse("https://0de4689a.ngrok.io");
    }
}
