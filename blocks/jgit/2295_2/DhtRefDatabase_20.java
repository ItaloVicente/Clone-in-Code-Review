
package org.eclipse.jgit.storage.dht;

class DhtReaderOptions {
	static final DhtReaderOptions DEFAULT = new DhtReaderOptions();

	private static final int MB = 1024 * 1024;

	private final Timeout timeout = Timeout.seconds(5);

	Timeout getTimeout() {
		return timeout;
	}

	Timeout getObjectListChunkTimeout() {
		return timeout;
	}

	boolean isValidateOnCopyAsIs() {
		return true;
	}

	int getPrefetchDepth() {
		return 25;
	}

	int getSelectObjectRepresentationConcurrentBatches() {
		return 8;
	}

	int getSelectObjectRepresentationBatchSize() {
		return 2048;
	}

	int getDeltaBaseCacheSize() {
		return 4096;
	}

	int getDeltaBaseCacheLimit() {
		return 64 * MB;
	}
}
