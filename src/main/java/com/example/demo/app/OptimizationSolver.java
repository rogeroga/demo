package com.example.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OptimizationSolver {

    @Autowired
    private Queue queue;

    @Value("${consumer.timeout}")
    private int timeout;


    public void solve(String data) throws Exception {

        int producers = 4;
        int consumers = 2;

        // Initialize the queue
        queue.init();

        // Simulate multiple parallel incoming requests
        // that is why few producer threads are created
        //
        for (int i = 0; i < producers; i++) {

            // A RequestProducer generates requests to the optimization solver
            // but first those requests would be placed in the BlockingQueue
            //
            new Thread( new RequestProducer(this.queue) ).start();

        }

        // After the requests payloads were pushed into the queue
        // Now let's simulate that multiple consumers would be taking values out of the BlockingQueue
        // And then is where the Optimizing Parallel solver would be called
        // In this way we can have a maximum number of parallel requests going to the optimizing solver
        // And we honor the license agreement
        //
        for (int i = 0; i < consumers; i++) {

            new Thread( new RequestConsumer(this.queue, this.timeout) ).start();

        }

        // Once that the producer and consumer threads were created they would be executing independently
        // Of the main thread and we only need to give them certain amount of time to simulate a
        // Real world scenario

        // Allow the threads to run for 12 seconds
        Thread.sleep(12 * 1000);

        // Terminate the application
        System.exit(0);
    }

}
