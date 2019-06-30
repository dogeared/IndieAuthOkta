package com.afitnerd.indieauth.IndieAuthOkta.service;

import com.afitnerd.indieauth.IndieAuthOkta.model.OAuthTokenResponse;

import java.io.IOException;

public interface OktaOAuthService {

    String buildAuthUrl(String state, String nonce);
    OAuthTokenResponse exchangeCodeForTokens(String code) throws IOException;
}
