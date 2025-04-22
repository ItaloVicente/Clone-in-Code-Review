
package org.eclipse.jgit.storage.dht.spi.cache;

import org.eclipse.jgit.storage.dht.Timeout;

public class CacheOptions {
	public Timeout getTimeout() {
		return Timeout.milliseconds(200);
	}

	public int getBufferSize() {
		return 512 * 1024;
	}
}
