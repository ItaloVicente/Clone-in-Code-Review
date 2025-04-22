
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PublisherPack {
	private final long packNumber;

	private List<PublisherPackSlice> dataSlices;

	private Collection<ReceiveCommand> commands;

	private String repositoryName;

	public PublisherPack(String name
			List<PublisherPackSlice> slices
		repositoryName = name;
		commands = updates;
		dataSlices = Collections.unmodifiableList(slices);
		packNumber = number;
	}

	public Iterator<PublisherPackSlice> getSlices() {
		return dataSlices.iterator();
	}

	public long getPackNumber() {
		return packNumber;
	}

	public boolean incrementOpen() {
		for (PublisherPackSlice s : dataSlices) {
			if (!s.incrementOpen()) {
				for (PublisherPackSlice s2 : dataSlices) {
					s2.release();
					if (s2 == s)
						return false;
				}
			}
		}
		return true;
	}

	public Collection<ReceiveCommand> getCommands() {
		return commands;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public boolean match(Collection<SubscribeSpec> subscribeSpecs) {
		for (ReceiveCommand cmd : commands) {
			boolean matched = false;
			for (SubscribeSpec spec : subscribeSpecs)
				if (spec.isMatch(cmd.getRefName())) {
					matched = true;
					break;
				}
			if (!matched)
				return false;
		}
		return true;
	}

	public void release() {
		for (PublisherPackSlice s : dataSlices)
			s.release();
	}
}
