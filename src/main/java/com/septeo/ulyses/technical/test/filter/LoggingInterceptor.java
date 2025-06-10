package com.septeo.ulyses.technical.test.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTimeThreadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = startTimeThreadLocal.get();
        long duration = System.currentTimeMillis() - startTime;
        startTimeThreadLocal.remove();

        String logEntry = String.format(
                "%s | %s | %s | %d | %d ms%n",
                LocalDateTime.now().format(formatter),
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration
        );

        writeLogToFile(logEntry);
    }

    private void writeLogToFile(String logEntry) {
        try (FileWriter fw = new FileWriter("api-requests.log", true)) {
            fw.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
