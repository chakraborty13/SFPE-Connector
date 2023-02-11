package com.f5.salesforce.emp.connector;

import org.eclipse.jetty.util.ajax.JSON;

import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An example of using the EMP connector which processes events synchronously
 *
 * @author sivananda
 * @since API v37.0
 */
public class DevLoginSynchronousEventProcessing extends  DevLoginApp {
	
	private final Logger logger = LoggerFactory.getLogger(DevLoginSynchronousEventProcessing.class);


    public static void main(String[] argv) throws Throwable {
        DevLoginSynchronousEventProcessing devLoginSynchronousEventProcessing = new DevLoginSynchronousEventProcessing();
        devLoginSynchronousEventProcessing.processEvents(argv);
    }

    @Override
    public Consumer<Map<String, Object>> getConsumer() {
    	logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return event -> logger.info(String.format("Received:\n%s, \nEvent processed by threadName:%s, threadId: %s", JSON.toString(event), Thread.currentThread().getName(), Thread.currentThread().getId()));
    }
}
