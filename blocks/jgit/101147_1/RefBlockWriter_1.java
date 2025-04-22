
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.RefBlockWriter.commonPrefix;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.INDEX_MAGIC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.util.IntList;

class IndexBlockWriter {
	private final List<BlockHeader> blocks = new ArrayList<>();
	private final Set<BlockHeader> restarts = new HashSet<>();
	private final int restartInterval;

	private int bytesInIndex;

	IndexBlockWriter(int ri) {
		restartInterval = ri;
	}

	boolean shouldWriteIndex() {
		return blocks.size() > 4;
	}

	int keysInIndex() {
		return blocks.size();
	}

	int bytesInIndex() {
		return bytesInIndex;
	}

	void addBlock(RefBlockWriter refBlock) {
		BlockHeader b = new BlockHeader(refBlock.lastRefName());
		byte[] name = b.lastRef;
		boolean restart = nextShouldBeRestart();
		if (!restart) {
			byte[] prior = blocks.get(blocks.size() - 1).lastRef;
			int pfx = commonPrefix(prior
			if (pfx == 0) {
				restart = true;
			}
		}

		blocks.add(b);
		if (restart) {
			restarts.add(b);
		}
	}

	private boolean nextShouldBeRestart() {
		int cnt = blocks.size();
		return cnt == 0 || ((cnt + 1) % restartInterval) == 0;
	}

	void writeTo(ReftableOutputStream os) throws IOException {
		os.write(INDEX_MAGIC);

		IntList restartOffsets = new IntList(restarts.size());
		for (int blkIdx = 0; blkIdx < blocks.size(); blkIdx++) {
			BlockHeader b = blocks.get(blkIdx);
			byte[] name = b.lastRef;
			boolean restart = restarts.contains(b);
			if (restart) {
				restartOffsets.add(os.bytesWrittenInBlock());
				os.writeVarint(0);
				os.writeVarint(name.length << 2);
				os.write(name);
			} else {
				byte[] prior = blocks.get(blkIdx - 1).lastRef;
				int pfx = commonPrefix(prior
				int sfx = name.length - pfx;
				os.writeVarint(pfx);
				os.writeVarint(sfx << 2);
				os.write(name
			}
			os.writeVarint(blkIdx);
		}
		int keysEnd = os.bytesWrittenInBlock();

		for (int i = 0; i < restartOffsets.size(); i++) {
			os.writeInt32(restartOffsets.get(i));
		}
		os.writeInt32(keysEnd);
		os.writeInt32(restartOffsets.size());
		bytesInIndex = os.bytesWrittenInBlock();
	}

	private static class BlockHeader {
		final byte[] lastRef;

		BlockHeader(String name) {
			lastRef = name.getBytes(UTF_8);
		}
	}
}
