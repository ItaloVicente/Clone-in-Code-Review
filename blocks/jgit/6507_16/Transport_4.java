
package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Subscriber {
	private final Map<String
			repoSubscriptions = new HashMap<String

	private String restartToken;

	private String lastPackNumber;

	public String getRestartToken() {
		return restartToken;
	}

	public void setRestartToken(String restart) {
		restartToken = restart;
	}

	public String getLastPackNumber() {
		return lastPackNumber;
	}

	public void setLastPackNumber(String number) {
		lastPackNumber = number;
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
		for (SubscribedRepository sr : repoSubscriptions.values()) {
			if (sr != null)
				sr.setSubscribeSpecs(clearSpecs);
		}
		setRestartToken(null);
		setLastPackNumber(null);
	}

	public void close() {
		for (SubscribedRepository sr : repoSubscriptions.values()) {
			if (sr != null)
				sr.close();
		}
		repoSubscriptions.clear();
	}
}
