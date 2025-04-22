
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class PublisherPack {
	private final List<PublisherPackSlice> dataSlices;

	public PublisherPack(List<PublisherPackSlice> slices) {
		dataSlices = Collections.unmodifiableList(slices);
	}

	public void writeToStream(OutputStream out) throws IOException {
		for (PublisherPackSlice s : dataSlices)
			s.writeToStream(out);
	}

	public void close() throws PublisherException {
		for (PublisherPackSlice s : dataSlices)
			s.close();
	}

	@Override
	public String toString() {
		return "PublisherPack[slices=" + dataSlices.size() + "]";
	}
}
