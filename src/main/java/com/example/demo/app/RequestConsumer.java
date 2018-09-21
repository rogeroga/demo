package com.example.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class RequestConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestConsumer.class);

    private Queue queue;

    private int timeout;

    private static int OPTIMIZING_SOLVER_EXPECTED_SOLVE_TIME = 1;

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
     * and set a time out each request to the expected time allowed
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
                                // A call to the Optimizing Solver is simulated here by spending time
                                //
                                // To create timeout scenario increase the value of this constant
                                // to something bigger than the timeout in the configuration
                                //
                                Thread.sleep(OPTIMIZING_SOLVER_EXPECTED_SOLVE_TIME * 1000);

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
