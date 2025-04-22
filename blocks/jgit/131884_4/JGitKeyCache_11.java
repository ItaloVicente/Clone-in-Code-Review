package org.eclipse.jgit.transport.sshd;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.eclipse.jgit.annotations.NonNull;

public class JGitHostConfigEntry extends HostConfigEntry {

	private Map<String

	public void setMultiValuedOptions(Map<String
		multiValuedOptions = options;
	}

	@NonNull
	public Map<String
		Map<String
		if (options == null) {
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(options);
	}

}
