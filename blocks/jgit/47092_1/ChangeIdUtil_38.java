
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;

import java.util.Set;

import org.eclipse.jgit.util.StringUtils;

public class UserAgent {
	private static volatile String userAgent = computeUserAgent();

	private static String computeUserAgent() {
	}

	private static String computeVersion() {
		Package pkg = UserAgent.class.getPackage();
		if (pkg != null) {
			String ver = pkg.getImplementationVersion();
			if (!StringUtils.isEmptyOrNull(ver)) {
				return ver;
			}
		}
	}

	private static String clean(String s) {
		s = s.trim();
		StringBuilder b = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c <= 32 || c >= 127) {
				if (b.length() > 0 && b.charAt(b.length() - 1) == '.')
					continue;
				c = '.';
			}
			b.append(c);
		}
		return b.length() > 0 ? b.toString() : null;
	}

	public static String get() {
		return userAgent;
	}

	public static void set(String agent) {
		userAgent = StringUtils.isEmptyOrNull(agent) ? null : clean(agent);
	}

	static String getAgent(Set<String> options
		if (options == null || options.isEmpty()) {
			return transportAgent;
		}
		for (String o : options) {
			if (o.startsWith(OPTION_AGENT)
					&& o.length() > OPTION_AGENT.length()
					&& o.charAt(OPTION_AGENT.length()) == '=') {
				return o.substring(OPTION_AGENT.length() + 1);
			}
		}
		return transportAgent;
	}

	static boolean hasAgent(Set<String> options) {
		return getAgent(options
	}

	private UserAgent() {
	}
}
