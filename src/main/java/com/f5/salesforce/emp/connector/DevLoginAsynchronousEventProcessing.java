package com.f5.salesforce.emp.connector;

import org.eclipse.jetty.util.ajax.JSON;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * An example of using the EMP connector which processes events asynchronously
 *
 * @author sivananda
 * @since API v37.0
 */
public class DevLoginAsynchronousEventProcessing extends DevLoginApp {

    // More than one thread can be used in the thread pool which leads to parallel processing of events which may be acceptable by the application
    // The main purpose of asynchronous event processing is to make sure that client is able to perform /meta/connect requests which keeps the session alive on the server side
    private final ExecutorService workerThreadPool = Executors.newFixedThreadPool(1);

    public static void main(String[] argv) throws Throwable {
        DevLoginAsynchronousEventProcessing devLoginAsynchronousEventProcessingExample = new DevLoginAsynchronousEventProcessing();
        devLoginAsynchronousEventProcessingExample.processEvents(argv);
    }

    @Override
public Consumer<Map<String, Object>> getConsumer() {
    // System.out.println("This is where we call okta methods to set partner flag");
    return event -> workerThreadPool.submit(
        () -> {
          System.out.println(String.format("ReceivedXXXX:\n%s, \nEvent processed by threadName:%s, threadId: %s",
              JSON.toString(event), Thread.currentThread().getName(), Thread.currentThread().getId()));
          /**
           * start okta process here - SATYA
           */

          String payload = JSON.toString(event);
          System.out.println("Okta processing here \n" + payload);
/**
          sdk = new OktaSdk(payload);
          System.out.println(sdk.getEmail("00u7s01kmxux9JZsM5d7"));
          System.out.println("Email from the message paylod is " + sdk.getPayloadEmail(payload));
          System.out.println("First Name from the message paylod is " + sdk.getPayloadFirstname(payload));
          System.out.println("Last Name from the message paylod is " + sdk.getPayloadLastname(payload));
          System.out.println("Division Id from the message paylod is " + sdk.getPayloadDivisionId(payload));

          // public User updateUser(String email, String firstName, String lastName,
          // String divisionId) {

          User updatedUser = sdk.updateUser(sdk.getPayloadEmail(payload),
              sdk.getPayloadFirstname(payload),
              sdk.getPayloadLastname(payload),
              sdk.getPayloadDivisionId(payload));
**/
        });
  }
}
