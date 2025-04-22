
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;

public class DhtMissingChunkException extends DhtException {
	private static final long serialVersionUID = 1L;

	private final ChunkKey chunkKey;

	public DhtMissingChunkException(ChunkKey key) {
		super(MessageFormat.format(DhtText.get().missingChunk
		chunkKey = key;
	}

	public DhtMissingChunkException(ChunkKey key
		this(key);
		initCause(why);
	}

	public ChunkKey getChunkKey() {
		return chunkKey;
	}
}
