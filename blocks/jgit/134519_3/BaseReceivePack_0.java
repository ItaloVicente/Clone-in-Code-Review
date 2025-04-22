package org.eclipse.jgit.internal.transport.parser;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toSet;

import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;

public final class FirstCommand {
	private final String line;
	private final Set<String> capabilities;

	public static FirstCommand fromLine(String line) {
		int nul = line.indexOf('\0');
		if (nul < 0) {
			return new FirstCommand(line
		}
		Set<String> opts =
					.stream()
					.collect(toSet());
		return new FirstCommand(line.substring(0
	}

	private FirstCommand(String line
		this.line = line;
		this.capabilities = capabilities;
	}

	@NonNull
	public String getLine() {
		return line;
	}

	@NonNull
	public Set<String> getCapabilities() {
		return capabilities;
	}
}
