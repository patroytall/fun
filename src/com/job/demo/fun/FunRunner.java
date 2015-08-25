package com.job.demo.fun;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FunRunner {
	private static final int GENERATOR_COUNT = 10;
	
	private final ExecutorService executorService;
	private final FunEngine funEngine = new FunEngine();
	private final List<FunGenerator> funGenerators = new LinkedList<FunGenerator>();
	private final List<Future<String>> futures = new LinkedList<Future<String>>();
	
	public FunRunner() {
		// enough threads for everyone
		executorService = Executors.newFixedThreadPool(GENERATOR_COUNT);
	}
	
	/**
	 * Sets fun level randomly between 1 and 10
	 * @param funGenerator
	 */
	private void setFunGeneratorFunLevel(FunGenerator funGenerator) {
		int funLevel = 1 + (int)(Math.random() * 10);
		funGenerator.setFunLevel(funLevel);
	}
	
	public void launchFunGenerators() {
		for (int i = 0; i < GENERATOR_COUNT; ++ i) {
			FunGenerator funGenerator = FunGeneratorFactory.getInstance(funEngine);
			setFunGeneratorFunLevel(funGenerator);
			funGenerators.add(funGenerator);
			Future<String> future = executorService.submit(funGenerator);
			futures.add(future);
		}		
	}

	private void printResults(List<String> results) {
		for (String result: results) {
			FunEngine.println(result);
		}
	}
	
	private void handleResults() {
		List<String> results = new LinkedList<String>();
		for (Future<String> future: futures) {
			String result;
			try {
				result = future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			results.add(result);
		}
		printResults(results);
	}
	
	/**
	 * gentle party stop that lets the generators finish in an orderly manner as the fun bursts end
	 */
	public void stopParty() {
		for (FunGenerator funGenerator: funGenerators) {
			funGenerator.stop();
		}
		handleResults();
	}
	
	
	public void abort() {
		executorService.shutdownNow();
	}
}