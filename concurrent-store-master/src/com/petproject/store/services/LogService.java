package com.petproject.store.services;

import java.util.Date;
import java.util.logging.*;

public class LogService {

    public final Logger log = Logger.getLogger(LogService.class.getName());

    private final String format = "\u001B[34m [%1$tF %1$tT] [%2$-10s] [%3$-30s] \u001B[37m%4$s %n";

    public LogService() {
        Formatter formatter = new Formatter() {
            @Override
            public String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        Thread.currentThread().getName(),
                        lr.getSourceClassName().replaceFirst(".*[.]", ""),
                        lr.getMessage()
                );
            }
        };
        log.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        log.addHandler(handler);
    }

}
