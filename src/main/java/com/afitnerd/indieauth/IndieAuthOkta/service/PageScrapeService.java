package com.afitnerd.indieauth.IndieAuthOkta.service;

import java.io.IOException;

public interface PageScrapeService {

    String REL_AUTHORIZATION_ENDPOINT = "authorization_endpoint";

    String findAuthorizationTag(String url) throws IOException;
}
