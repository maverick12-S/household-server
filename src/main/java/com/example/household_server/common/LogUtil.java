package com.example.household_server.common;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.household_server.domain.model.Transaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogUtil {

    private static final Logger accessLogger = LoggerFactory.getLogger("ACCESS_LOGGER");
    private static final Logger errorLogger = LoggerFactory.getLogger("ERROR_LOGGER");
    private static final Logger debugLogger = LoggerFactory.getLogger("DEBUG_LOGGER");

    public void logAccess(HttpServletRequest request, HttpServletResponse response) {
        accessLogger.info("{} {} from {} -> {}",
            request.getMethod(),
            request.getRequestURI(),
            request.getLocalPort(),
            response.getStatus()
        );
    }

    public void logError(HttpServletRequest request, Exception ex) {
        errorLogger.error("{} - {} from {}",
            request.getRequestURI(),
            ex.getClass().getSimpleName(),
            request.getLocalPort(),
            ex
        );
    }

    public void logDebug(Transaction tx) {
        debugLogger.debug("{} [DEBUG] {}", LocalDateTime.now(), tx);
    }
}
