
package org.eclipse.jgit.internal.storage.reftable;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.RAW_IDLEN;
import static org.eclipse.jgit.internal.storage.reftable.ReftableOutputStream.computeVarintSize;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.IntList;

class RefBlockWriter {
	private final List<Ref> refs = new ArrayList<>();
	private final Set<Ref> restarts = new HashSet<>();
	private final int blockSize;
	private final int restartInterval;

	private int bytesInKeyTable;
	private int bytesInRestartTable;

	RefBlockWriter(int bs
		blockSize = bs;
		restartInterval = ri;
		bytesInRestartTable = 4 + 4;

		if (!tryAdd(ref
			throw blockSizeTooSmall(ref);
		}
	}

	String lastRefName() {
		return refs.get(refs.size() - 1).getName();
	}

	boolean tryAdd(Ref ref) {
		if (tryAdd(ref
			return true;
		} else if (nextShouldBeRestart()) {
			return tryAdd(ref
		}
		return false;
	}

	private boolean tryAdd(Ref ref
		byte[] name = nameUtf8(ref);
		int prefixLen = 0;
		int suffixLen = name.length;
		boolean restart = tryRestart && nextShouldBeRestart();
		if (!restart) {
			byte[] prior = nameUtf8(refs.get(refs.size() - 1));
			prefixLen = commonPrefix(prior
			if (prefixLen > 0) {
				suffixLen = name.length - prefixLen;
			} else {
				restart = true;
			}
		}

		int entrySize = computeEntrySize(prefixLen
		if (computeBlockSize(entrySize
			return false;
		}

		bytesInKeyTable += entrySize;
		refs.add(ref);
		if (restart) {
			bytesInRestartTable += 4;
			restarts.add(ref);
		}
		return true;
	}

	private boolean nextShouldBeRestart() {
		int cnt = refs.size();
		return cnt == 0 || ((cnt + 1) % restartInterval) == 0;
	}

	private int computeBlockSize(int key
		return bytesInKeyTable + key + bytesInRestartTable + (restart ? 4 : 0);
	}

	void writeTo(ReftableOutputStream os
			throws IOException {
		IntList restartOffsets = new IntList(restarts.size());
		for (int refIdx = 0; refIdx < refs.size(); refIdx++) {
			Ref ref = refs.get(refIdx);
			byte[] name = nameUtf8(ref);
			boolean restart = restarts.contains(ref);
			if (restart) {
				restartOffsets.add(os.bytesWrittenInBlock());
				os.writeVarint(0);
				os.writeVarint(encodeLenAndType(name.length
				os.write(name);
			} else {
				byte[] prior = nameUtf8(refs.get(refIdx - 1));
				int pfx = commonPrefix(prior
				int sfx = name.length - pfx;
				os.writeVarint(pfx);
				os.writeVarint(encodeLenAndType(sfx
				os.write(name
			}
			if (ref.isSymbolic()) {
				writeSymref(os
			} else {
				writeId(os
			}
		}
		int keysEnd = os.bytesWrittenInBlock();

		int padLen = os.bytesAvailableInBlock() - bytesInRestartTable;
		if (padBlock && padLen > 0) {
			os.pad(padLen);
		}

		for (int i = 0; i < restartOffsets.size(); i++) {
			os.writeInt32(restartOffsets.get(i));
		}
		os.writeInt32(keysEnd);
		os.writeInt32(restartOffsets.size());
		os.finishBlock(padBlock);
	}

	private void writeSymref(ReftableOutputStream os
			throws IOException {
		byte[] name = nameUtf8(target);
		os.writeVarint(name.length);
		os.write(name);
	}

	private void writeId(ReftableOutputStream os
		ObjectId id1 = ref.getObjectId();
		if (id1 == null) {
			if (ref.getStorage() == NEW) {
				return;
			} else {
				throw new IOException(JGitText.get().invalidId0);
			}
		} else if (!ref.isPeeled()) {
			throw new IOException(JGitText.get().peeledRefIsRequired);
		}
		os.write(id1);

		ObjectId id2 = ref.getPeeledObjectId();
		if (id2 != null) {
			os.write(id2);
		}
	}

	private BlockSizeTooSmallException blockSizeTooSmall(Ref ref) {
		byte[] name = nameUtf8(ref);
		int entry = computeEntrySize(0
		int min = computeBlockSize(entry
		return new BlockSizeTooSmallException(min);
	}

	private static int computeEntrySize(int prefixLen
		return computeVarintSize(prefixLen)
				+ computeVarintSize(encodeLenAndType(suffixLen
				+ suffixLen
				+ computeValueSize(ref);
	}

	private static int encodeLenAndType(int nameLen
		return (nameLen << 2) | type(ref);
	}

	private static int type(Ref ref) {
		if (ref.isSymbolic()) {
			return 0x03;
		} else if (ref.getStorage() == NEW && ref.getObjectId() == null) {
			return 0x00;
		} else if (ref.getPeeledObjectId() != null) {
			return 0x02;
		} else {
			return 0x01;
		}
	}

	private static int computeValueSize(Ref ref) {
		if (ref.isSymbolic()) {
			return nameUtf8(ref.getTarget()).length + 1;
		} else if (ref.getStorage() == NEW && ref.getObjectId() == null) {
			return 0;
		}
		return ref.getPeeledObjectId() != null ? 2 * RAW_IDLEN : RAW_IDLEN;
	}

	static int commonPrefix(byte[] a
		int len = Math.min(n
		for (int i = 0; i < len; i++) {
			if (a[i] != b[i]) {
				return i;
			}
		}
		return len;
	}

	private static byte[] nameUtf8(Ref ref) {
		return ref.getName().getBytes(UTF_8);
	}
}
