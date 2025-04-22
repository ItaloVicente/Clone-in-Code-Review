
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PublisherPack {
	private final long packNumber;

	private final List<PublisherPackSlice> dataSlices;

	private final Collection<ReceiveCommand> commands;

	private final Set<SubscribeSpec> existingSpecs;

	private final String repositoryName;

	public PublisherPack(String name
			List<PublisherPackSlice> slices
			Collection<SubscribeSpec> specs) {
		repositoryName = name;
		commands = updates;
		existingSpecs = new LinkedHashSet<SubscribeSpec>();
		existingSpecs.addAll(specs);
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
		for (SubscribeSpec s : subscribeSpecs)
			if (!existingSpecs.contains(s))
				return false;
		return true;
	}

	public void release() {
		for (PublisherPackSlice s : dataSlices)
			s.release();
	}

	@Override
	public String toString() {
		return "PublisherPack[num=" + packNumber + "
	}
}
