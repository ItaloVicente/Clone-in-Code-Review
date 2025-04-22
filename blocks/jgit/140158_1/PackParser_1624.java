
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jgit.lib.Ref;

public abstract class OperationResult {

	Map<String

	URIish uri;

	final SortedMap<String

	StringBuilder messageBuffer;

	String peerUserAgent;

	public URIish getURI() {
		return uri;
	}

	public Collection<Ref> getAdvertisedRefs() {
		return Collections.unmodifiableCollection(advertisedRefs.values());
	}

	public final Ref getAdvertisedRef(String name) {
		return advertisedRefs.get(name);
	}

	public Collection<TrackingRefUpdate> getTrackingRefUpdates() {
		return Collections.unmodifiableCollection(updates.values());
	}

	public TrackingRefUpdate getTrackingRefUpdate(String localName) {
		return updates.get(localName);
	}

	void setAdvertisedRefs(URIish u
		uri = u;
		advertisedRefs = ar;
	}

	void add(TrackingRefUpdate u) {
		updates.put(u.getLocalName()
	}

	public String getMessages() {
	}

	void addMessages(String msg) {
		if (msg != null && msg.length() > 0) {
			if (messageBuffer == null)
				messageBuffer = new StringBuilder();
			messageBuffer.append(msg);
				messageBuffer.append('\n');
		}
	}

	public String getPeerUserAgent() {
		return peerUserAgent;
	}
}
