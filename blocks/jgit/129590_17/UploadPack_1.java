package org.eclipse.jgit.internal.transport.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FirstWant {
	private final String line;

	private final Set<String> capabilities;

	public static FirstWant fromLine(String line) {
		String wantLine;
		Set<String> capabilities;

		if (line.length() > 45) {
			final HashSet<String> opts = new HashSet<>();
			String opt = line.substring(45);
				opt = opt.substring(1);
			}
				opts.add(c);
			}
			wantLine = line.substring(0
			capabilities = Collections.unmodifiableSet(opts);
		} else {
			wantLine = line;
			capabilities = Collections.emptySet();
		}

		return new FirstWant(wantLine
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
