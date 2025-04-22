package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.annotations.NonNull;

public interface SshConfigStore {

	@NonNull
	HostConfig lookup(@NonNull String hostName

	interface HostConfig {

		String getValue(String key);

		List<String> getValues(String key);

		@NonNull
		Map<String

		@NonNull
		Map<String

	}

	static final HostConfig EMPTY_CONFIG = new HostConfig() {

		@Override
		public String getValue(String key) {
			return null;
		}

		@Override
		public List<String> getValues(String key) {
			return Collections.emptyList();
		}

		@Override
		public Map<String
			return Collections.emptyMap();
		}

		@Override
		public Map<String
			return Collections.emptyMap();
		}

	};
}
