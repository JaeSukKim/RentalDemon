package com.rental.demon.core.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogFileCron {

    @Value("${rental.api.access-log.directory}")
    private String API_ACCESS_LOG_DIR;

    @Value("${rental.api.access-log.directory}")
    private String API_APP_LOG_DIR;

    @Value("${rental.api.access-log.directory}")
    private String ADMIN_ACCESS_LOG_DIR;


}
