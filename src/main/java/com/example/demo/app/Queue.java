package com.example.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class Queue {

    private static final Logger logger = LoggerFactory.getLogger(Queue.class);

    // Underlying Blocking Queue
    // used to control and limit the number of parallel executions
    //
    private BlockingQueue<Object> queue;

    // Configurable maximum limit of parallel executions of the optimization solver
    // found in the properties file for each environment
    //
    @Value("${queue.limit}")
    private int limit;

    public boolean init() {
        try {
            // initialize the blocking queue
            if (limit > 0) {
                queue = new ArrayBlockingQueue<>(limit);

                logger.info("Blocking queue created of size: " + limit);
            }
        }
        catch(Exception ex) {
            logger.error("Error creating blocking queue: " + ex.getMessage());
            return false;
        }

        return true;
    }

    public int getLimit()
    {
        return limit;
    }

    public int size() {
        return queue.size();
    }

    public boolean put(Object obj) throws InterruptedException {
        try {
            queue.put(obj);
        }
        catch (InterruptedException ex) {
            logger.error("Error adding request object to the controlling queue: " + ex.getMessage());

            throw ex;
        }

        return true;
    }

    public Object take() throws InterruptedException {
        Object obj;
        try {
            obj = queue.take();
        }
        catch (InterruptedException ex) {
            logger.error("Error taking request object from the controlling queue: " + ex.getMessage());

            throw ex;
        }

        return obj;
    }

}
