package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_AGENT;
import static org.eclipse.jgit.transport.GitProtocolConstants.OPTION_SERVER_OPTION;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;

public class CommandCapabilities {

	protected List<String> serverOptions = new ArrayList<>();

	@Nullable
	protected String agent;

	public String getAgent() {
		return agent;
	}

	public List<String> getServerOptions() {
		return serverOptions;
	}

	@Nullable
	private String safePayloadExtraction(String line
		if (line != null && line.length() > keyPrefix.length()
				&& line.startsWith(keyPrefix)) {
			return line.substring(keyPrefix.length());
		}

		return null;
	}

	public void addCmdCapability(String line) {
		String serverOption = safePayloadExtraction(line
		if (serverOption != null) {
			serverOptions.add(serverOption);
			return;
		}

		String agentPayload = safePayloadExtraction(line
		if (agentPayload != null) {
			agent = agentPayload;
			return;
		}
	}
}
