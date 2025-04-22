
package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubscribeState {
	private final Map<String
			repoSubscriptions = new HashMap<String

	private String restartToken;

	private String lastPackId;

	public String getRestartToken() {
		return restartToken;
	}

	public void setRestartToken(String restart) {
		restartToken = restart;
	}

	public String getLastPackId() {
		return lastPackId;
	}

	public void setLastPackId(String id) {
		lastPackId = id;
	}

	public void putRepository(String r
		repoSubscriptions.put(r
	}

	public SubscribedRepository getRepository(String r) {
		return repoSubscriptions.get(r);
	}

	public Set<String> getAllRepositories() {
		return Collections.unmodifiableSet(repoSubscriptions.keySet());
	}

	public void reset() {
		List<RefSpec> clearSpecs = Collections.emptyList();
		for (SubscribedRepository sr : repoSubscriptions.values())
			sr.setSubscribeSpecs(clearSpecs);
		setRestartToken(null);
		setLastPackId(null);
	}

	public void close() {
		for (SubscribedRepository sr : repoSubscriptions.values())
			sr.close();
		repoSubscriptions.clear();
	}
}
