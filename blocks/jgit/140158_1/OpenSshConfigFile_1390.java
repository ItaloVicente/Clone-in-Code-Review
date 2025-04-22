package org.eclipse.jgit.internal.transport.parser;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;

public class FirstWant {
	private final String line;

	private final Set<String> capabilities;

	@Nullable
	private final String agent;

	private static final String AGENT_PREFIX = OPTION_AGENT + '=';

	public static FirstWant fromLine(String line) throws PackProtocolException {
		String wantLine;
		Set<String> capabilities;
		String agent = null;

		if (line.length() > 45) {
			String opt = line.substring(45);
				throw new PackProtocolException(JGitText.get().wantNoSpaceWithCapabilities);
			}
			opt = opt.substring(1);

			HashSet<String> opts = new HashSet<>();
				if (clientCapability.startsWith(AGENT_PREFIX)) {
					agent = clientCapability.substring(AGENT_PREFIX.length());
				} else {
					opts.add(clientCapability);
				}
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
			@Nullable String agent) {
		this.line = line;
		this.capabilities = capabilities;
		this.agent = agent;
	}

	public String getLine() {
		return line;
	}

	public Set<String> getCapabilities() {
		return capabilities;
	}

	@Nullable
	public String getAgent() {
		return agent;
	}
}
