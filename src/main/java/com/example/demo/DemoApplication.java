package com.example.demo;

import com.example.demo.app.OptimizationSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private OptimizationSolver solver;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		solver.solve("{\"requests\": 100}");

		// Allow the simulation to run for 12 seconds
		Thread.sleep(10 * 1000);

		// End of simulation - exit the system and shut down the program gracefully
		System.exit(0);
	}

}
