
package org.eclipse.jgit.storage.dht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkPrefetch {
	public static class Hint {
		private final List<ChunkKey> edge;

		private final List<ChunkKey> sequential;

		Hint(List<ChunkKey> edge
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

		TinyProtobuf.Encoder toBytes() {
			int max = (2 * ChunkKey.SHORT_KEYLEN) * edge.size();
			max += (2 * ChunkKey.SHORT_KEYLEN) * sequential.size();
			TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
			for (ChunkKey key : edge)
				e.bytes(1
			for (ChunkKey key : sequential)
				e.bytes(2
			return e;
		}

		static Hint fromBytes(ChunkKey key
			RepositoryKey repo = key.getRepositoryKey();
			ArrayList<ChunkKey> edge = null;
			ArrayList<ChunkKey> sequential = null;

			PARSE: for (;;) {
				switch (d.next()) {
				case 0:
					break PARSE;
				case 1:
					if (edge == null)
						edge = new ArrayList<ChunkKey>(4);
					edge.add(ChunkKey.fromShortBytes(repo
					continue;
				case 2:
					if (sequential == null)
						sequential = new ArrayList<ChunkKey>(4);
					sequential.add(ChunkKey.fromShortBytes(repo
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

			return new Hint(edge
		}
	}

	public static ChunkPrefetch fromBytes(ChunkKey key
		Hint commit = null;
		Hint tree = null;
		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				commit = Hint.fromBytes(key
				continue;
			case 2:
				tree = Hint.fromBytes(key
				continue;
			default:
				d.skip();
				continue;
			}
		}

		return new ChunkPrefetch(commit
	}

	private final Hint commits;

	private final Hint trees;

	ChunkPrefetch(Hint commit
		this.commits = commit;
		this.trees = tree;
	}

	public Hint getCommits() {
		return commits;
	}

	public Hint getTrees() {
		return trees;
	}

	boolean isEmpty() {
		boolean commitsEmpty = commits == null || commits.isEmpty();
		boolean treesEmpty = trees == null || trees.isEmpty();
		return commitsEmpty && treesEmpty;
	}

	public byte[] toBytes() {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(256);
		if (commits != null)
			e.message(1
		if (trees != null)
			e.message(2
		return e.asByteArray();
	}
}
