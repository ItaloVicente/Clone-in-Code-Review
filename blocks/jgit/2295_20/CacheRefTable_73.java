
package org.eclipse.jgit.storage.dht.spi.cache;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.storage.dht.Timeout;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public class CacheOptions {
	private Timeout timeout;

	private int writeBufferSize;

	public CacheOptions() {
		setTimeout(Timeout.milliseconds(500));
		setWriteBufferSize(512 * 1024);
	}

	public Timeout getTimeout() {
		return timeout;
	}

	public CacheOptions setTimeout(Timeout maxWaitTime) {
		if (maxWaitTime == null || maxWaitTime.getTime() < 0)
			throw new IllegalArgumentException();
		timeout = maxWaitTime;
		return this;
	}

	public int getWriteBufferSize() {
		return writeBufferSize;
	}

	public CacheOptions setWriteBufferSize(int sizeInBytes) {
		writeBufferSize = Math.max(1024
		return this;
	}

	public CacheOptions fromConfig(final Config rc) {
		setTimeout(Timeout.getTimeout(rc
		setWriteBufferSize(rc.getInt("cache"
		return this;
	}
}
