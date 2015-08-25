package com.job.demo.invoke;

import com.job.demo.fun.FunRunner;

public class FunDemo {
	private static final int PARTY_TIME_SECONDS = 5;

	/**
	 * This application calls methods from different threads for fun.
	 * @param args
	 */
	public static void main(String[] args) {
		FunRunner funRunner = new FunRunner();
		boolean completed = false;
		try {
			funRunner.launchFunGenerators();
			
			// wait for the generators to have some fun
			try {
				Thread.sleep(PARTY_TIME_SECONDS * 1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			
			// pack your bags and go home
			funRunner.stopParty();
			
			completed = true;
		} finally {
			// ensure proper thread clean up for clean JVM exit
			if (!completed) {
				funRunner.abort();
			}
		}
	}
}