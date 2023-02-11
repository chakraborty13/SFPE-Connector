package com.f5.salesforce.emp.connector;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingListener implements ClientSessionChannel.MessageListener {
	
	private final Logger logger = LoggerFactory.getLogger(LoggingListener.class);
	

    private boolean logSuccess;
    private boolean logFailure;

    public LoggingListener() {
        this.logSuccess = true;
        this.logFailure = true;
    }

    public LoggingListener(boolean logSuccess, boolean logFailure) {
        this.logSuccess = logSuccess;
        this.logFailure = logFailure;
    }

    @Override
    public void onMessage(ClientSessionChannel clientSessionChannel, Message message) {
        if (logSuccess && message.isSuccessful()) {
            logger.info(">>>>");
            printPrefix();
            logger.info("Success:[" + clientSessionChannel.getId() + "]");
            logger.info(message.toString());
            logger.info("<<<<");
        }

        if (logFailure && !message.isSuccessful()) {
            logger.info(">>>>");
            printPrefix();
            logger.info("Failure:[" + clientSessionChannel.getId() + "]");
            logger.info(message.toString());
            logger.info("<<<<");
        }
    }

    private void printPrefix() {
        System.out.print("[" + timeNow() + "] ");
    }

    private String timeNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        return dateFormat.format(now);
    }
}
