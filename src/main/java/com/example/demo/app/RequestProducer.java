package com.example.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RequestProduced is going to place the requests in the Blocking Queue
 * Its job is to push simulated payload requests into the Blocking Queue
 *
 * In order to simulate multiple parallel incoming requests I implemented the Runnable interface
 * To create multiple producer threads pushing requests payloads into the single Blocking Queue
 */
public class RequestProducer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestProducer.class);

    private Queue queue;

    public RequestProducer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try
        {
            while (true)
            {
                Object payload = getPayload();

                queue.put(payload);

                logger.info("Submitting payload: " + payload + " - Queue size: "  + queue.size());
            }
        }
        catch (InterruptedException ex)
        {
            logger.info("Producer interrupted");
        }
    }

    Object getPayload()
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
            logger.info("Producer read interrupted");
        }

        return new Object();
    }

}
