package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;

public class DhtMissingChunkException extends DhtException {
	private static final long serialVersionUID = 1L;

	public DhtMissingChunkException(ChunkKey key) {
		super(MessageFormat.format(DhtText.get().missingChunk
	}

	public DhtMissingChunkException(ChunkKey key
		this(key);
		initCause(why);
	}
}
