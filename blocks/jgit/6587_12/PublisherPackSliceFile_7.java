
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class PublisherPackSlice {
	public interface LoadPolicy {
		public boolean shouldLoad(PublisherPackSlice slice);
	}

	public interface LoadCallback {
		public void loaded(PublisherPackSlice slice);

		public void unloaded(PublisherPackSlice slice);
	}

	private static final int WRITE_SIZE = 4096;

	private final int size;

	private byte memoryBuffer[];

	private volatile boolean inMemory = true;

	private volatile boolean closed;

	private final AtomicInteger referenceCount = new AtomicInteger();

	private final LoadPolicy loadPolicy;

	private final LoadCallback loadCallback;

	final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public PublisherPackSlice(LoadPolicy policy
			int consumers
		loadPolicy = policy;
		loadCallback = callback;
		referenceCount.set(consumers);
		size = buf.length;
		memoryBuffer = buf;
	}

	public long getSize() {
		return size;
	}

	public void load() throws IOException {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			if (inMemory)
				return;
			memoryBuffer = doLoad();
			inMemory = true;
		} finally {
			writeLock.unlock();
		}
		loadCallback.loaded(this);
	}

	protected boolean store(boolean allocated) throws IOException {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			if (!inMemory)
				return false;
			doStore(memoryBuffer);
			inMemory = false;
			memoryBuffer = null;
			if (allocated)
				loadCallback.unloaded(this);
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean store() throws IOException {
		return store(true);
	}

	abstract protected byte[] doLoad() throws IOException;

	abstract protected void doStore(byte[] buffer) throws IOException;

	abstract protected void doStoredWrite(
			OutputStream out

	public void writeToStream(OutputStream out) throws IOException {
		if (loadPolicy.shouldLoad(this))
			load();
		int pos = 0;
		while (pos < size) {
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				if (closed)
					throw new IOException("Slice already closed");
				int writeLen = Math.min(size - pos
				if (inMemory)
					out.write(memoryBuffer
				else
					doStoredWrite(out
				pos += writeLen;
			} finally {
				readLock.unlock();
			}
		}
	}

	public boolean incrementOpen() {
		return referenceCount.getAndIncrement() > 0;
	}

	public void release() {
		if (referenceCount.decrementAndGet() == 0) {
			if (inMemory)
				loadCallback.unloaded(this);
			close();
		}
	}

	public boolean canRecycle() {
		return referenceCount.get() == 0 && closed;
	}

	public void close() {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			inMemory = false;
			memoryBuffer = null;
			closed = true;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isLoaded() {
		return inMemory;
	}
}
