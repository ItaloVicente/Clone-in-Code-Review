
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PushResult extends OperationResult {
	private Map<String

	public Collection<RemoteRefUpdate> getRemoteUpdates() {
		return Collections.unmodifiableCollection(remoteUpdates.values());
	}

	public RemoteRefUpdate getRemoteUpdate(String refName) {
		return remoteUpdates.get(refName);
	}

	void setRemoteUpdates(
			final Map<String
		this.remoteUpdates = remoteUpdates;
	}
}
