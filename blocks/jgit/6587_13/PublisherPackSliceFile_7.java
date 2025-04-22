
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class PublisherPackSlice {
	public interface LoadPolicy {
		public boolean shouldLoad(PublisherPackSlice slice);
	}

	public interface Deallocator {
		public void deallocate();
	}

	public interface Allocator {
		public Deallocator allocate(PublisherPackSlice slice);
	}

	private static final int WRITE_SIZE = 4096;

	private final int size;

	private byte memoryBuffer[];

	private volatile boolean closed;

	private final LoadPolicy loadPolicy;

	private final Allocator memoryAllocator;

	private volatile Deallocator deallocator;

	final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public PublisherPackSlice(
			LoadPolicy policy
		loadPolicy = policy;
		memoryAllocator = callback;
		size = buf.length;
		memoryBuffer = buf;
	}

	void setDeallocator(Deallocator d) {
		deallocator = d;
	}

	public long getSize() {
		return size;
	}

	public void load() throws IOException {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			if (isLoaded())
				return;
			memoryBuffer = doLoad();
		} finally {
			writeLock.unlock();
		}
		deallocator = memoryAllocator.allocate(this);
	}

	protected boolean store() throws IOException {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			if (!isLoaded())
				return false;
			doStore(memoryBuffer);
			memoryBuffer = null;
			if (isLoaded()) {
				deallocator.deallocate();
				deallocator = null;
			}
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	abstract protected byte[] doLoad() throws IOException;

	abstract protected void doStore(byte[] buffer) throws IOException;

	abstract protected void doStoredWrite(
			OutputStream out

	void writeToStream(OutputStream out) throws IOException {
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
				if (isLoaded())
					out.write(memoryBuffer
				else
					doStoredWrite(out
				pos += writeLen;
			} finally {
				readLock.unlock();
			}
		}
	}

	public void close() {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			memoryBuffer = null;
			closed = true;
			if (isLoaded()) {
				deallocator.deallocate();
				deallocator = null;
			}
		} finally {
			writeLock.unlock();
		}
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isLoaded() {
		return (deallocator != null);
	}
}
