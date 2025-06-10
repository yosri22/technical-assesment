package com.septeo.ulyses.technical.test.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Value("${logging.interceptor.log-file-path:api-requests.log}")
    private String logFilePath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTimeThreadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = startTimeThreadLocal.get();
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            startTimeThreadLocal.remove();

            String logEntry = String.format(
                    "%s | %s | %s | %d | %d ms",
                    LocalDateTime.now().format(FORMATTER),
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration
            );

            log.info(logEntry);
            writeLogToFile(logEntry);
        }
    }

    private void writeLogToFile(String logEntry) {
        try (FileWriter fw = new FileWriter(logFilePath, true)) {
            fw.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            log.error("Failed to write log entry to file: {}", logFilePath, e);
        }
    }

}
