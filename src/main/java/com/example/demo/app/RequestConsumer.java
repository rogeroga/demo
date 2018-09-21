package com.example.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class RequestConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestConsumer.class);

    private Queue queue;

    private int timeout;

    private static int OPTIMIZING_SOLVER_AVG_CALL_TIME = 1;

    public RequestConsumer(Queue queue, int timeout) {
        this.queue = queue;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try
        {
            while (true)
            {
                Object payload = queue.take();

                logger.info("Consumer retrieved payload: " + payload + " - Queue size: "  + queue.size());

                // Now finally call the optimization solver
                //
                solve(payload);

            }
        }
        catch (InterruptedException ex)
        {
            logger.info("Consumer thread interrupted");
        }
    }

    /**
     * Here is where is expected to call the optimization solver
     * and time out each request to the maximum time allowed
     *
     * @param payload
     */
    void solve(Object payload)
    {
        // Implement time out for calls to the optimization solver
        //
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(
            (Callable) () -> {
                                // Simulate a call to the optimizing solver by spending time
                                // To create timeout increase this value
                                Thread.sleep(OPTIMIZING_SOLVER_AVG_CALL_TIME * 1000);

                                return "Ok - for payload: " + payload;
                            });

        boolean interrupted = false;
        try {
            // Call the optimization solver and set a timeout in seconds
            //
            String result = future.get(this.timeout, TimeUnit.SECONDS);

            logger.info("Result: " + result);
        }
        catch (TimeoutException | InterruptedException | ExecutionException e) {
            logger.info("Timeout - Consumer thread interrupted");
            interrupted = true;
        }

        executor.shutdownNow();

        logger.info("Optimization call for payload:" + payload + " request timed out: " + (interrupted ? "YES" : "NO"));

    }
}
