package org.eclipse.jgit.internal.transport.sshd;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.eclipse.jgit.annotations.NonNull;

public class JGitHostConfigEntry extends HostConfigEntry {

	private Map<String

	@Override
	public String getProperty(String name
		Map<String
		if (properties == null || properties.isEmpty()) {
			return defaultValue;
		}
		return super.getProperty(name
	}

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
