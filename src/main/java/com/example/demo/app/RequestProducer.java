package com.example.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
