
package org.eclipse.jgit.storage.dht;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.Constants;

class DhtInserterOptions {
	static final DhtInserterOptions DEFAULT = new DhtInserterOptions();

	private static final int MB = 1024 * 1024;

	private static final SecureRandom prng = new SecureRandom();

	int getChunkSize() {
		return 1 * MB;
	}

	int getMaxObjectCount() {
		return (getChunkSize() - 2) / (Constants.OBJECT_ID_LENGTH + 4);
	}

	int getCompressionLevel() {
		return Deflater.DEFAULT_COMPRESSION;
	}

	int getPrefetchDepth() {
		return 50;
	}

	int getParserCacheSize() {
		return 512;
	}

	int nextChunkSalt() {
		return prng.nextInt();
	}

	int getObjectListCommitsToSkip() {
		final int avgCommitsPerChunk = 2200;
		return avgCommitsPerChunk * 2;
	}

	int getObjectListSecondsToSkip() {
		return (int) TimeUnit.SECONDS.convert(30
	}
}
