package com.job.demo.fun;

import java.util.concurrent.Callable;

public interface IFunGenerator extends Callable<String> {
	/**
	 * @param funLevel
	 *            from 0 not much fun to 10 greatest fun
	 */
	public void setFunLevel(int funLevel);

	/**
	 * Stop the fun generations
	 */
	public void stop();
}