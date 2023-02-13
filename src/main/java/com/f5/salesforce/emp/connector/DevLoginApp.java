/*
 * Copyright (c) 2016, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE.TXT file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.f5.salesforce.emp.connector;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import org.eclipse.jetty.util.ajax.JSON;

import com.f5.salesforce.emp.bayeux.BayeuxParameters;
import com.f5.salesforce.emp.bayeux.EmpConnector;
import com.f5.salesforce.emp.bayeux.LoginHelper;
import com.f5.salesforce.emp.bayeux.TopicSubscription;
import com.f5.salesforce.emp.connector.properties.EnvMap;

import static org.cometd.bayeux.Channel.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An example of using the EMP connector
 *
 * @author hal.hildebrand
 * @since API v37.0
 */
public abstract class DevLoginApp {

	private final Logger logger = LoggerFactory.getLogger(DevLoginApp.class);
	private static EnvMap envMap = new EnvMap();
	
	private static final String sfUrl = envMap.getEnv("SF_URL");
	private static final String sfUsername = envMap.getEnv("SF_USERNAME");
	private static final String sfCreds = envMap.getEnv("SF_CREDS");
	private static final String sfTopic = envMap.getEnv("SF_TOPIC");
	private static final long sfReplayFrom = Long.parseLong(envMap.getEnv("SF_REPLAY_FROM"));	
	
	public void processEvents(String[] argv) throws Throwable {
		logger.info("SF parameters receiveds \n{} \n{} \n{} \n{} {}",sfUrl,sfUsername,sfCreds,sfTopic,sfReplayFrom);
		logger.info("LOGGER TEST FROM DebLoginApp");
		
		/**
		if (argv.length < 4 || argv.length > 5) {
			System.err.println("Usage: DevLoginExample url username password topic [replayFrom]");
			System.exit(1);
		}
		**/

		BearerTokenProvider tokenProvider = new BearerTokenProvider(() -> {
			try {
				return LoginHelper.login(new URL(sfUrl), sfUsername,sfCreds );
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		BayeuxParameters params = tokenProvider.login();

		EmpConnector connector = new EmpConnector(params);
		LoggingListener loggingListener = new LoggingListener(true, true);

		connector.addListener(META_HANDSHAKE, loggingListener).addListener(META_CONNECT, loggingListener)
				.addListener(META_DISCONNECT, loggingListener).addListener(META_SUBSCRIBE, loggingListener)
				.addListener(META_UNSUBSCRIBE, loggingListener);

		connector.setBearerTokenProvider(tokenProvider);

		connector.start().get(5, TimeUnit.SECONDS);

		long replayFrom = EmpConnector.REPLAY_FROM_EARLIEST;
		if (argv.length == 5) {
			replayFrom = Long.parseLong(argv[4]);
		}
		TopicSubscription subscription;
		try {
			subscription = connector.subscribe(sfTopic, sfReplayFrom, getConsumer()).get(5, TimeUnit.SECONDS);
		} catch (ExecutionException e) {
			System.err.println(e.getCause().toString());
			System.exit(1);
			throw e.getCause();
		} catch (TimeoutException e) {
			System.err.println("Timed out subscribing");
			System.exit(1);
			throw e.getCause();
		}

		logger.info(String.format("Subscribed: %s", subscription));
	}

	public abstract Consumer<Map<String, Object>> getConsumer();
}
