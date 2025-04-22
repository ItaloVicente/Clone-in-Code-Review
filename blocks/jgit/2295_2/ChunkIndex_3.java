
package org.eclipse.jgit.storage.dht;

import java.util.ArrayList;
import java.util.List;

public class ChunkFragments {
	public static ChunkFragments fromBytes(ChunkKey key
		RepositoryKey repo = key.getRepositoryKey();
		List<ChunkKey> chunks = new ArrayList<ChunkKey>(4);
		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				chunks.add(ChunkKey.fromShortBytes(repo
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return new ChunkFragments(chunks);
	}

	private final List<ChunkKey> chunks;

	ChunkFragments(List<ChunkKey> chunks) {
		this.chunks = chunks;
	}

	public int size() {
		return chunks.size();
	}

	public ChunkKey getChunkKey(int idx) {
		return chunks.get(idx);
	}

	public ChunkKey getNextFor(ChunkKey chunkKey) {
		for (int i = 0; i < chunks.size() - 1; i++) {
			if (chunks.get(i).equals(chunkKey))
				return chunks.get(i + 1);
		}
		return null;
	}

	public byte[] toBytes() {
		int cnt = chunks.size();
		int max = cnt * (2 + ChunkKey.SHORT_KEYLEN);
		TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
		for (ChunkKey key : chunks)
			e.bytes(1
		return e.asByteArray();
	}
}
