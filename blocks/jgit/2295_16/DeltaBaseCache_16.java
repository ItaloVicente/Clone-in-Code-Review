
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkMeta {
	public static ChunkMeta fromBytes(ChunkKey key
		return fromBytes(key
	}

	public static ChunkMeta fromBytes(ChunkKey key
		List<BaseChunk> baseChunk = null;
		List<ChunkKey> fragment = null;
		PrefetchHint commit = null;
		PrefetchHint tree = null;

		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				if (baseChunk == null)
					baseChunk = new ArrayList<BaseChunk>(4);
				baseChunk.add(BaseChunk.fromBytes(d.message()));
				continue;
			case 2:
				if (fragment == null)
					fragment = new ArrayList<ChunkKey>(4);
				fragment.add(ChunkKey.fromBytes(d));
				continue;
			case 51:
				commit = PrefetchHint.fromBytes(d.message());
				continue;
			case 52:
				tree = PrefetchHint.fromBytes(d.message());
				continue;
			default:
				d.skip();
				continue;
			}
		}

		return new ChunkMeta(key
	}

	private final ChunkKey chunkKey;

	List<BaseChunk> baseChunks;

	List<ChunkKey> fragments;

	PrefetchHint commitPrefetch;

	PrefetchHint treePrefetch;

	ChunkMeta(ChunkKey key) {
		this(key
	}

	ChunkMeta(ChunkKey chunkKey
			List<ChunkKey> fragment
		this.chunkKey = chunkKey;
		this.baseChunks = baseChunk;
		this.fragments = fragment;
		this.commitPrefetch = commit;
		this.treePrefetch = tree;
	}

	public ChunkKey getChunkKey() {
		return chunkKey;
	}

	BaseChunk getBaseChunk(long position) throws DhtException {

		int high = baseChunks.size();
		int low = 0;
		while (low < high) {
			final int mid = (low + high) >>> 1;
			final BaseChunk base = baseChunks.get(mid);

			if (position > base.relativeStart) {
				low = mid + 1;

			} else if (mid == 0 || position == base.relativeStart) {
				return base;

			} else if (baseChunks.get(mid - 1).relativeStart < position) {
				return base;

			} else {
				high = mid;
			}
		}

		throw new DhtException(MessageFormat.format(
				DhtText.get().missingLongOffsetBase
				Long.valueOf(position)));
	}

	public int getFragmentCount() {
		return fragments != null ? fragments.size() : 0;
	}

	public ChunkKey getFragmentKey(int nth) {
		return fragments.get(nth);
	}

	public ChunkKey getNextFragment(ChunkKey currentKey) {
		for (int i = 0; i < fragments.size() - 1; i++) {
			if (fragments.get(i).equals(currentKey))
				return fragments.get(i + 1);
		}
		return null;
	}

	public PrefetchHint getCommitPrefetch() {
		return commitPrefetch;
	}

	public PrefetchHint getTreePrefetch() {
		return treePrefetch;
	}

	boolean isEmpty() {
		if (baseChunks != null && !baseChunks.isEmpty())
			return false;
		if (fragments != null && !fragments.isEmpty())
			return false;
		if (commitPrefetch != null && !commitPrefetch.isEmpty())
			return false;
		if (treePrefetch != null && !treePrefetch.isEmpty())
			return false;
		return true;
	}

	public byte[] asBytes() {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(256);

		if (baseChunks != null) {
			for (BaseChunk base : baseChunks)
				e.message(1
		}

		if (fragments != null) {
			for (ChunkKey key : fragments)
				e.bytes(2
		}

		if (commitPrefetch != null)
			e.message(51
		if (treePrefetch != null)
			e.message(52

		return e.asByteArray();
	}

	public static class BaseChunk {
		final long relativeStart;

		private final ChunkKey chunk;

		BaseChunk(long relativeStart
			this.relativeStart = relativeStart;
			this.chunk = chunk;
		}

		public long getRelativeStart() {
			return relativeStart;
		}

		public ChunkKey getChunkKey() {
			return chunk;
		}

		TinyProtobuf.Encoder asBytes() {
			int max = 11 + 2 + ChunkKey.KEYLEN;
			TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
			e.int64(1
			e.bytes(2
			return e;
		}

		static BaseChunk fromBytes(TinyProtobuf.Decoder d) {
			long relativeStart = -1;
			ChunkKey chunk = null;

			PARSE: for (;;) {
				switch (d.next()) {
				case 0:
					break PARSE;
				case 1:
					relativeStart = d.int64();
					continue;
				case 2:
					chunk = ChunkKey.fromBytes(d);
					continue;
				default:
					d.skip();
					continue;
				}
			}

			return new BaseChunk(relativeStart
		}
	}

	public static class PrefetchHint {
		private final List<ChunkKey> edge;

		private final List<ChunkKey> sequential;

		PrefetchHint(List<ChunkKey> edge
			if (edge == null)
				edge = Collections.emptyList();
			else
				edge = Collections.unmodifiableList(edge);

			if (sequential == null)
				sequential = Collections.emptyList();
			else
				sequential = Collections.unmodifiableList(sequential);

			this.edge = edge;
			this.sequential = sequential;
		}

		public List<ChunkKey> getEdge() {
			return edge;
		}

		public List<ChunkKey> getSequential() {
			return sequential;
		}

		boolean isEmpty() {
			return edge.isEmpty() && sequential.isEmpty();
		}

		TinyProtobuf.Encoder asBytes() {
			int max = 0;

			max += (2 + ChunkKey.KEYLEN) * edge.size();
			max += (2 + ChunkKey.KEYLEN) * sequential.size();

			TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
			for (ChunkKey key : edge)
				e.bytes(1
			for (ChunkKey key : sequential)
				e.bytes(2
			return e;
		}

		static PrefetchHint fromBytes(TinyProtobuf.Decoder d) {
			ArrayList<ChunkKey> edge = null;
			ArrayList<ChunkKey> sequential = null;

			PARSE: for (;;) {
				switch (d.next()) {
				case 0:
					break PARSE;
				case 1:
					if (edge == null)
						edge = new ArrayList<ChunkKey>(16);
					edge.add(ChunkKey.fromBytes(d));
					continue;
				case 2:
					if (sequential == null)
						sequential = new ArrayList<ChunkKey>(16);
					sequential.add(ChunkKey.fromBytes(d));
					continue;
				default:
					d.skip();
					continue;
				}
			}

			if (edge != null)
				edge.trimToSize();

			if (sequential != null)
				sequential.trimToSize();

			return new PrefetchHint(edge
		}
	}
}
