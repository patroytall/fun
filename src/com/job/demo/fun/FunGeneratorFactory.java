package com.job.demo.fun;

public class FunGeneratorFactory {
	static int id = 0;
	
	private static synchronized int getNextId() {
		id++;
		return id;
	}
	
	public static FunGenerator getInstance(FunEngine funEngine) {
		return new FunGenerator(funEngine, getNextId());
	}
}
