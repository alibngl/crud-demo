package com.crudDemo.crudDemo.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    private static final Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void testLog() {
        logger.info("info message.");
        logger.debug("debug message.");
        logger.warn("warn message.");
        logger.error("error message.");
    }
}

