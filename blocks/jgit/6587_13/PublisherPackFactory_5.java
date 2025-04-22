
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PublisherPack {
	private final List<PublisherPackSlice> dataSlices;

	private final Set<SubscribeSpec> existingSpecs;

	public PublisherPack(List<PublisherPackSlice> slices
			Collection<SubscribeSpec> specs) {
		existingSpecs = new LinkedHashSet<SubscribeSpec>();
		existingSpecs.addAll(specs);
		dataSlices = Collections.unmodifiableList(slices);
	}

	public void writeToStream(OutputStream out) throws IOException {
		for (PublisherPackSlice s : dataSlices)
			s.writeToStream(out);
	}

	public boolean match(Collection<SubscribeSpec> subscribeSpecs) {
		for (SubscribeSpec s : subscribeSpecs)
			if (!existingSpecs.contains(s))
				return false;
		return true;
	}

	public void close() {
		for (PublisherPackSlice s : dataSlices)
			s.close();
	}

	@Override
	public String toString() {
		return "PublisherPack[" + existingSpecs + "
				+ dataSlices.size() + "]";
	}
}
