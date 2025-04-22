package org.eclipse.jgit.internal.transport.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FirstWant {
	private final String line;

	private final Set<String> capabilities;

	public static FirstWant fromLine(String line) {
		String realLine;
		Set<String> options;

		if (line.length() > 45) {
			final HashSet<String> opts = new HashSet<>();
			String opt = line.substring(45);
				opt = opt.substring(1);
				opts.add(c);
			realLine = line.substring(0
			options = Collections.unmodifiableSet(opts);
		} else {
			realLine = line;
			options = Collections.emptySet();
		}

		return new FirstWant(realLine
	}

	private FirstWant(String line
		this.line = line;
		this.capabilities = capabilities;
	}

	public String getLine() {
		return line;
	}

	public Set<String> getCapabilities() {
		return capabilities;
	}
}
