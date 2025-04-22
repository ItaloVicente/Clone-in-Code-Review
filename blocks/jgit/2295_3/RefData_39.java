
package org.eclipse.jgit.storage.dht;

import java.io.IOException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.storage.dht.DhtReader.ChunkAndOffset;
import org.eclipse.jgit.storage.dht.RefData.IdWithChunk;

final class RecentChunks {
	private final DhtReader reader;

	private final int maxSize;

	private int curSize;

	private Node lruHead;

	private Node lruTail;

	RecentChunks(DhtReader reader) {
		this.reader = reader;
		this.maxSize = reader.getOptions().getRecentChunkCacheSize();
	}

	PackChunk get(ChunkKey key) {
		for (Node n = lruHead; n != null; n = n.next) {
			if (key.equals(n.chunk.getChunkKey())) {
				hit(n);
				return n.chunk;
			}
		}
		return null;
	}

	void put(PackChunk chunk) {
		for (Node n = lruHead; n != null; n = n.next) {
			if (n.chunk == chunk) {
				hit(n);
				return;
			}
		}

		Node n;
		if (curSize < maxSize) {
			n = new Node();
			curSize++;
		} else {
			n = lruTail;
			remove(n);
		}
		n.chunk = chunk;
		first(n);
	}

	ObjectLoader open(RepositoryKey repo
			throws IOException {
		if (objId instanceof IdWithChunk) {
			PackChunk chunk = get(((IdWithChunk) objId).getChunkKey());
			if (chunk != null) {
				int pos = chunk.findOffset(repo
				if (0 <= pos)
					return PackChunk.read(chunk
			}

		}

		for (Node n = lruHead; n != null; n = n.next) {
			int pos = n.chunk.findOffset(repo
			if (0 <= pos) {
				hit(n);
				return PackChunk.read(n.chunk
			}
		}

		return null;
	}

	ChunkAndOffset find(RepositoryKey repo
		if (objId instanceof IdWithChunk) {
			PackChunk chunk = get(((IdWithChunk) objId).getChunkKey());
			if (chunk != null) {
				int pos = chunk.findOffset(repo
				if (0 <= pos)
					return new ChunkAndOffset(chunk
			}

		}

		for (Node n = lruHead; n != null; n = n.next) {
			int pos = n.chunk.findOffset(repo
			if (0 <= pos) {
				hit(n);
				return new ChunkAndOffset(n.chunk
			}
		}

		return null;
	}

	boolean has(RepositoryKey repo
		for (Node n = lruHead; n != null; n = n.next) {
			int pos = n.chunk.findOffset(repo
			if (0 <= pos) {
				hit(n);
				return true;
			}
		}
		return false;
	}

	void clear() {
		curSize = 0;
		lruHead = null;
		lruTail = null;
	}

	private void hit(Node n) {
		if (lruHead != n) {
			remove(n);
			first(n);
		}
	}

	private void remove(Node node) {
		Node p = node.prev;
		Node n = node.next;

		if (p != null)
			p.next = n;
		if (n != null)
			n.prev = p;

		if (lruHead == node)
			lruHead = n;
		if (lruTail == node)
			lruTail = p;
	}

	private void first(Node n) {
		n.prev = null;
		n.next = lruHead;

		lruHead = n;

		if (lruTail == null)
			lruTail = n;
	}

	private static class Node {
		PackChunk chunk;

		Node prev;

		Node next;
	}
}
