package com.afitnerd.indieauth.IndieAuthOkta.controller;

import com.afitnerd.indieauth.IndieAuthOkta.model.AuthResponse;
import com.afitnerd.indieauth.IndieAuthOkta.model.StartAuthRequest;
import com.afitnerd.indieauth.IndieAuthOkta.model.CodeAuthRequest;
import com.afitnerd.indieauth.IndieAuthOkta.service.OktaOAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@RestController
public class AuthController {

    @Value("#{ @environment['indieauth.authorization.redirect-uri'] }")
    private String redirectUri;

    private OktaOAuthService oktaOAuthService;

    private static final String INDIE_AUTH_STATE = "indieAuthState";
    private static final String OKTA_OAUTH_STATE = "oktaOAuthState";
    private static final String OKTA_OAUTH_NONCE = "oktaOAuthNonce";

    public AuthController(OktaOAuthService oktaOAuthService) {
        this.oktaOAuthService = oktaOAuthService;
    }

    @GetMapping("/auth")
    public void auth(
        StartAuthRequest authRequest, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        if (!redirectUri.equals(authRequest.getRedirectUri())) {
            throw new RuntimeException("Redirect Uri: " + authRequest.getRedirectUri() + " is not allowed.");
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(INDIE_AUTH_STATE, authRequest.getState());
        String state = UUID.randomUUID().toString();
        String nonce = UUID.randomUUID().toString();
        session.setAttribute(OKTA_OAUTH_STATE, state);
        session.setAttribute(OKTA_OAUTH_NONCE, nonce);
        response.sendRedirect(oktaOAuthService.buildAuthUrl(state, nonce));
    }

    @GetMapping("/callback")
    public void callback(
        CodeAuthRequest authRequest, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        HttpSession session = request.getSession();
        String indieAuthState = (String) session.getAttribute(INDIE_AUTH_STATE);
        Assert.hasText(indieAuthState, "IndieAuth State not found");
        response.sendRedirect(redirectUri + "?state=" + indieAuthState + "&code=" + authRequest.getCode());
    }

    @PostMapping("/auth")
    public @ResponseBody
    AuthResponse auth(@ModelAttribute CodeAuthRequest request) {

        // exchange code for id token

        return new AuthResponse("https://0de4689a.ngrok.io");
    }
}
