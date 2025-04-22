
package org.eclipse.jgit.storage.pack;

import java.util.concurrent.locks.ReentrantLock;

class ThreadSafeDeltaCache extends DeltaCache {
	private final ReentrantLock lock;

	ThreadSafeDeltaCache(PackWriter pw) {
		super(pw);
		lock = new ReentrantLock();
	}

	@Override
	boolean canCache(int length
		lock.lock();
		try {
			return super.canCache(length
		} finally {
			lock.unlock();
		}
	}

	@Override
	void credit(int reservedSize) {
		lock.lock();
		try {
			super.credit(reservedSize);
		} finally {
			lock.unlock();
		}
	}

	@Override
	Ref cache(byte[] data
		data = resize(data
		lock.lock();
		try {
			return super.cache(data
		} finally {
			lock.unlock();
		}
	}
}
