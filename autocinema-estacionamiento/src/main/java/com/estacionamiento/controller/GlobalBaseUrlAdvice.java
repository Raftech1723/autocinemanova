package com.estacionamiento.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalBaseUrlAdvice {
	
	@ModelAttribute("baseUrl")
	public String getBaseUrl(HttpServletRequest request) {
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        String forwardedProto = request.getHeader("X-Forwarded-Proto");

        if (forwardedHost != null && forwardedProto != null) {
            return forwardedProto + "://" + forwardedHost;
        }

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        return scheme + "://" + serverName + ":" + serverPort;
    }


}
