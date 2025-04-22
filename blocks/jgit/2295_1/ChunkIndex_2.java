
package org.eclipse.jgit.storage.dht;

import java.util.ArrayList;
import java.util.List;

public class ChunkFragments {
	static ChunkFragments fromRaw(byte[] raw) {
		List<ChunkKey> chunks = new ArrayList<ChunkKey>(4);
		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				chunks.add(ChunkKey.fromBytes(d.bytes()));
				continue;
			default:
				d.skip();
				continue;
			}
		}
		return new ChunkFragments(chunks);
	}

	static byte[] toByteArray(List<ChunkKey> fragmentList) {
		int cnt = fragmentList.size();
		int max = cnt * (2 + ChunkKey.KEYLEN);
		TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
		for (ChunkKey key : fragmentList)
			e.bytes(1
		return e.asByteArray();
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
}
