
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jgit.transport.PublisherPackSlice.Allocator;
import org.eclipse.jgit.transport.PublisherPackSlice.Deallocator;

public class PublisherMemoryPool implements Allocator {
	private final ConcurrentLinkedQueue<PublisherPackSlice> loadedSlices;

	private final long capacity;

	private final AtomicLong size;

	public PublisherMemoryPool(long capacity) {
		this.capacity = capacity;
		loadedSlices = new ConcurrentLinkedQueue<PublisherPackSlice>();
		size = new AtomicLong();
	}

	public Deallocator allocate(final PublisherPackSlice slice) {
		if (loadedSlices.contains(slice))
			throw new IllegalStateException("Slice " + slice
					+ " already allocated");
		size.addAndGet(slice.getSize());

		for (PublisherPackSlice s : loadedSlices) {
			if (size.get() <= capacity)
				break;
			try {
				s.store();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		loadedSlices.add(slice);
		return new Deallocator() {
			public void deallocate() {
				if (!loadedSlices.remove(slice))
					throw new IllegalStateException("Slice " + slice
							+ " already deallocated");
				size.addAndGet(-slice.getSize());
			}
		};
	}
}
