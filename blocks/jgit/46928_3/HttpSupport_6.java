
package org.eclipse.jgit.transport;

public class UserAgent {
	private static volatile String userAgent = computeUserAgent();

	private static String computeUserAgent() {
	}

	private static String computeVersion() {
		Package pkg = UserAgent.class.getPackage();
		if (pkg != null) {
			String ver = pkg.getImplementationVersion();
			if (ver != null)
				return ver;
		}
	}

	private static String clean(String s) {
		s = s.trim();
		StringBuilder b = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c <= 32 || c >= 127) {
				if (i > 0 && b.charAt(i - 1) == '.')
					continue;
				c = '.';
			}
			b.append(c);
		}
		return b.toString();
	}

	public static String get() {
		return userAgent;
	}

	public static void set(String agent) {
		userAgent = clean(agent);
	}

	private UserAgent() {
	}
}
