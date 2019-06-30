package com.afitnerd.indieauth.IndieAuthOkta.service;

public interface OktaOAuthService {

    String buildAuthUrl(String state, String nonce);
}
