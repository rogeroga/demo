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

        int numProducers = 2;
        int numConsumers = 1;

        // Initialize the queue
        queue.init();

        // Simulate multiple parallel incoming requests
        //
        for (int i = 0; i < numProducers; i++) {

            // A RequestProducer generates requests to the optimization solver
            // but first those requests would be placed in the BlockingQueue
            //
            new Thread( new RequestProducer(this.queue) ).start();

        }

        // Simulate multiple parallel requests would come out of the BlockingQueue
        //
        for (int i = 0; i < numConsumers; i++) {

            new Thread( new RequestConsumer(this.queue, this.timeout) ).start();

        }

        // Allow the threads to run for 10 seconds
        Thread.sleep(10 * 1000);

        // Terminate the application
        System.exit(0);
    }

}
