
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.Constants;

class DhtReaderOptions {
	static final DhtReaderOptions DEFAULT = new DhtReaderOptions();

	private static final int MB = 1024 * 1024;

	private final Timeout timeout = Timeout.seconds(5);

	Timeout getTimeout() {
		return timeout;
	}

	boolean isValidateOnCopyAsIs() {
		return true;
	}

	int getSelectObjectRepresentationConcurrentBatches() {
		return 4;
	}

	int getSelectObjectRepresentationBatchSize() {
		return (1 * MB) / (Constants.OBJECT_ID_STRING_LENGTH + 54);
	}

	int getDeltaBaseCacheEntryCount() {
		return 16;
	}

	int getDeltaBaseCacheSize() {
		return 10 * MB;
	}
}
