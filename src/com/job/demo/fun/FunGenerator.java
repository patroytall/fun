package com.job.demo.fun;

public class FunGenerator implements IFunGenerator {
	private static final String HAPPY_STR = "happy";
	private static final String UNHAPPY_STR = "unhappy";
	private static final int DEFAULT_FUN_LEVEL = 1;
	private static final int BASE_REST_TIME_SECONDS = 3;
	private static final int FUN_LEVEL_THRESHOLD = 5;

	private final FunEngine funEngine;
	private final int id;

	private Boolean stopped = false;
	private int funLevel = DEFAULT_FUN_LEVEL;


	/**
	 * @param funEngine
	 *            engine where the party occurs
	 * @param id
	 *            unique id of the generator
	 */
	FunGenerator(FunEngine funEngine, int id) {
		this.funEngine = funEngine;
		this.id = id;
	}
	
	private String getResult(int funEventCount) {
		String status = funEventCount > FUN_LEVEL_THRESHOLD? HAPPY_STR : UNHAPPY_STR;
		return id + " is " + status + " because he had fun "  + funEventCount + " times";
		
	}
	
	private void funBurst() {
		// the more fun you have the less chance of sleeping
		int divider = 1 + (int)(Math.random() * funLevel);
		try {
			Thread.sleep(BASE_REST_TIME_SECONDS * 1000 / divider);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		funEngine.party(id);
		
	}
	
	@Override
	public String call() {
		int funEventCount = 0;
		while (true) {
			synchronized(stopped) {
				if (stopped) {
					break;
				}
			}
			funBurst();
			funEventCount++;
		}
		return getResult(funEventCount);
	}

	@Override
	public void setFunLevel(int funLevel) {
		if (funLevel < 1 || funLevel > 10) {
			throw new RuntimeException("invalid fun level: " + funLevel);
		}
		this.funLevel = funLevel;
	}

	@Override
	public void stop() {
		synchronized(stopped) {
			stopped = true;
		}
	}
}