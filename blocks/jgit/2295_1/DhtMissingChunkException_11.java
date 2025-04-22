
package org.eclipse.jgit.storage.dht;

import java.util.zip.Deflater;

class DhtInserterOptions {
	static final DhtInserterOptions DEFAULT = new DhtInserterOptions();

	private static final int MB = 1024 * 1024;

	int getChunkSize() {
		return 1 * MB;
	}

	int getCompressionLevel() {
		return Deflater.DEFAULT_COMPRESSION;
	}
}
