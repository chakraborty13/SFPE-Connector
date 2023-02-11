package com.f5.salesforce.emp.connector;

import org.eclipse.jetty.util.ajax.JSON;

import com.f5.salesforce.emp.connector.okta.OktaSdk;
import com.okta.sdk.resource.user.User;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DevLoginAsynchronousEventProcessing extends DevLoginApp {

	private final Logger logger = LoggerFactory.getLogger(DevLoginAsynchronousEventProcessing.class);

	// More than one thread can be used in the thread pool which leads to parallel
	// processing of events which may be acceptable by the application
	// The main purpose of asynchronous event processing is to make sure that client
	// is able to perform /meta/connect requests which keeps the session alive on
	// the server side
	private final ExecutorService workerThreadPool = Executors.newFixedThreadPool(1);

	public static void main(String[] argv) throws Throwable {
		DevLoginAsynchronousEventProcessing devLoginAsynchronousEventProcessingExample = new DevLoginAsynchronousEventProcessing();
		devLoginAsynchronousEventProcessingExample.processEvents(argv);
	}

	@Override
	public Consumer<Map<String, Object>> getConsumer() {

		return event -> workerThreadPool.submit(() -> {
			logger.info(String.format("Received:\n%s, \nEvent processed by threadName:%s, threadId: %s",
					JSON.toString(event), Thread.currentThread().getName(), Thread.currentThread().getId()));
			String payload = JSON.toString(event);
			/**
			 * logger.info(sdk.getEmail("00u7s01kmxux9JZsM5d7"));
			 * logger.info("Email from the message paylod is " +
			 * sdk.getPayloadEmail(payload)); logger.info("First Name from the
			 * message paylod is " + sdk.getPayloadFirstname(payload));
			 * logger.info("Last Name from the message paylod is " +
			 * sdk.getPayloadLastname(payload)); logger.info("Division Id from the
			 * message paylod is " + sdk.getPayloadDivisionId(payload));
			 */
			OktaSdk sdk = new OktaSdk(payload);

			try {
				User updatedUser = sdk.updateUser(sdk.getPayloadEmail(payload), sdk.getPayloadFirstname(payload),
						sdk.getPayloadLastname(payload), sdk.getPayloadDivisionId(payload));
				logger.info("Partner Flag Update successfull for %s", updatedUser.getProfile().getEmail());
			} catch (Exception e) {
				logger.error("Erron updating okta for %s \n %s", sdk.getPayloadEmail(payload), e);
			}

		});
	}
}
