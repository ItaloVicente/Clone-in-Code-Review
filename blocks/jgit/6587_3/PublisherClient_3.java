
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jgit.util.ConcurrentLinkedList;

public class PublisherBuffer {

	private final ConcurrentLinkedList<PublisherPackSlice> loadedSlices;

	private final ScheduledExecutorService gcService;

	private int gcInterval;

	private final long capacity;

	private final AtomicLong size;

	private boolean gcStarted;

	public PublisherBuffer(long capacity) {
		this.capacity = capacity;
		loadedSlices = new ConcurrentLinkedList<PublisherPackSlice>();
		gcInterval = DEFAULT_GC_INTERVAL;
		gcService = new ScheduledThreadPoolExecutor(1);
		size = new AtomicLong();
	}

	public void setGcInterval(int interval) throws IllegalStateException {
		if (gcStarted)
			throw new IllegalStateException();
		gcInterval = interval;
	}

	public void allocate(PublisherPackSlice slice) {
		size.addAndGet(slice.getSize());
		if (size.get() > capacity) {
			PublisherPackSlice s;
			for (Iterator<PublisherPackSlice> it = loadedSlices
					.getWriteIterator(); size.get() > capacity
					&& it.hasNext();) {
				s = it.next();
				try {
					s.store();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		loadedSlices.put(slice);
	}

	public void deallocate(PublisherPackSlice slice) {
		size.addAndGet(-slice.getSize());
	}

	public void startGC() {
		if (gcStarted)
			throw new IllegalStateException();
		gcStarted = true;
		gcService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				Iterator<PublisherPackSlice> it = loadedSlices
						.getWriteIterator();
				for (PublisherPackSlice slice = it.next(); it.hasNext();) {
					if (slice.isClosed())
						it.remove();
				}
			}
		}
	}
}
