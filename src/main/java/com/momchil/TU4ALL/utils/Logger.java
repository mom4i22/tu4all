package com.momchil.TU4ALL.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class Logger {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    public final static int _LOGLEVEL = 2;
    public final static int _ERROR_LEVEL = 0;
    public final static int _WARNING_LEVEL = 1;
    public final static int _DEBUG_LEVEL = 2;

    private final static SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");

    public synchronized static void log(String logMessage) {

        System.err.println(logMessage);
    }

    public synchronized static void log(int logLevel, String logMessage) {

//        String message = Constants._PRODUCT_NAME + Constants._PRODUCT_VERSION + " | " + " " + formatter.format(new Date(System.currentTimeMillis())) + logMessage;

        if (logLevel <= _LOGLEVEL) {
//                System.err.println(message);
            if (logLevel == _DEBUG_LEVEL) {
                if (logger != null)
                    logger.debug(logMessage);
                else
                    System.out.println(logMessage);
            } else if (logLevel == _ERROR_LEVEL) {
                if (logger != null)
                    logger.error(logMessage);
                else
                    System.err.println(logMessage);
            } else {
                if (logger != null)
                    logger.info(logMessage);
                else
                    System.out.println(logMessage);
            }
        }
    }

    public synchronized static void log(int logLevel, HttpSession session, HttpServletRequest request) {

        String host = request.getServerName();
        String protocol = request.getScheme();
        String remoteHost = request.getRemoteHost();
        String remoteAddress = request.getRemoteAddr();
        String context = request.getContextPath();
        String sessionId = session.getId();
        String currentPage = request.getServletPath();
        int port = request.getServerPort();

        String logMessage = "resource = " + currentPage
                + " protocol = " + protocol
                + " host = " + host
                + " port = " + port
                + " context = " + context
                + " remoteHost = " + remoteHost
                + " remoteAddress = " + remoteAddress
                + " sessionId = " + sessionId;

        Logger.log(logLevel, logMessage);

    }
}