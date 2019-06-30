package com.afitnerd.indieauth.IndieAuthOkta.controller;

import com.afitnerd.indieauth.IndieAuthOkta.model.AuthResponse;
import com.afitnerd.indieauth.IndieAuthOkta.model.OAuthTokenResponse;
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
import java.util.Arrays;
import java.util.UUID;

@RestController
public class AuthController {

    @Value("#{ @environment['indieauth.authorization.me-uri'] }")
    private String meUri;

    @Value("#{ @environment['indieauth.authorization.redirect-uris'] }")
    private String[] redirectUris;

    private OktaOAuthService oktaOAuthService;

    private static final String INDIE_AUTH_STATE = "indieAuthState";
    private static final String INDIE_AUTH_REDIRECT = "indieAuthRedirect";

    private static final String OKTA_OAUTH_STATE = "oktaOAuthState";

    public AuthController(OktaOAuthService oktaOAuthService) {
        this.oktaOAuthService = oktaOAuthService;
    }

    @GetMapping("/auth")
    public void auth(
        StartAuthRequest authRequest, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        validateRedirectUri(authRequest);
        HttpSession session = request.getSession(true);
        session.setAttribute(INDIE_AUTH_STATE, authRequest.getState());
        session.setAttribute(INDIE_AUTH_REDIRECT, authRequest.getRedirectUri());
        String state = UUID.randomUUID().toString();
        String nonce = UUID.randomUUID().toString();
        session.setAttribute(OKTA_OAUTH_STATE, state);
        response.sendRedirect(oktaOAuthService.buildAuthUrl(state, nonce));
    }

    private void validateRedirectUri(StartAuthRequest authRequest) {
        Arrays.stream(redirectUris)
            .filter(authRequest.getRedirectUri()::equals)
            .findFirst()
            .orElseThrow(
                () -> new RuntimeException("Redirect Uri: " + authRequest.getRedirectUri() + " is not allowed.")
            );
    }

    @GetMapping("/callback")
    public void callback(
        CodeAuthRequest authRequest, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        HttpSession session = request.getSession();

        String indieAuthState = (String) session.getAttribute(INDIE_AUTH_STATE);
        Assert.hasText(indieAuthState, "IndieAuth state not found");
        String indieAuthRedirect = (String) session.getAttribute(INDIE_AUTH_REDIRECT);
        Assert.hasText(indieAuthState, "IndieAuth redirect not found");
        String oktaAuthState = (String) session.getAttribute(OKTA_OAUTH_STATE);
        Assert.isTrue(authRequest.getState().equals(oktaAuthState), "Okta state value doesn't match");

        response.sendRedirect(indieAuthRedirect + "?state=" + indieAuthState + "&code=" + authRequest.getCode());
    }

    @PostMapping("/auth")
    public @ResponseBody
    AuthResponse auth(@ModelAttribute CodeAuthRequest authRequest) throws IOException {

        // exchange code for id token
        OAuthTokenResponse tokenResponse = oktaOAuthService.exchangeCodeForTokens(authRequest.getCode());

        return new AuthResponse(meUri);
    }
}
