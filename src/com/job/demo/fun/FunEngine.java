package com.job.demo.fun;

/**
 * Thread safe fun engine
 */
public class FunEngine {
	public static synchronized void println(String str) {
		System.out.println(str);
		// flush to ensure fun is reported immediately
		System.out.flush();
	}
	
	private static void printPartyLine(int id) {
		String message = id + " is having fun";
		println(message);
	}
	
	public void party(int id) {
		printPartyLine(id);
	}
}
