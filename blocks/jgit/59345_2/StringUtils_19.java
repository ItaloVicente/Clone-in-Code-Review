
package org.eclipse.jgit.util;

public final class Debug {

	private Debug() {
	}

	public static boolean isInfo() {
		return false;
	}

	public static boolean isDetail() {
		return false;
	}

	public static void println(String message) {
		System.out.println(message);
	}
}
