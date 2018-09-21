package com.example.demo;

import com.example.demo.app.OptimizationSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private OptimizationSolver optimizationSolver;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// The solver simulates a real world scenario by
		// generating requests placing them in the blocking queue and
		// have consumers taking out values of the queue
		// and then calling the optimization solver
		//
		String data = "{\"requests\": [{\"id\": 10, \"data\": \"ABCD\"}, {\"id\": 30, \"data\": \"XYZ\"}]}";

		optimizationSolver.solve(data);

	}

}
