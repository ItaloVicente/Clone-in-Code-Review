
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

class PackIndexV1 extends PackIndex {
	private static final int IDX_HDR_LEN = 256 * 4;

	private final long[] idxHeader;

	byte[][] idxdata;

	private long objectCnt;

	PackIndexV1(final InputStream fd
			throws CorruptObjectException
		final byte[] fanoutTable = new byte[IDX_HDR_LEN];
		System.arraycopy(hdr
		IO.readFully(fd

		for (int k = 0; k < idxHeader.length; k++)
			idxHeader[k] = NB.decodeUInt32(fanoutTable
		idxdata = new byte[idxHeader.length][];
		for (int k = 0; k < idxHeader.length; k++) {
			int n;
			if (k == 0) {
				n = (int) (idxHeader[k]);
			} else {
				n = (int) (idxHeader[k] - idxHeader[k - 1]);
			}
			if (n > 0) {
				final long len = n * (Constants.OBJECT_ID_LENGTH + 4);
					throw new IOException(JGitText.get().indexFileIsTooLargeForJgit);

				idxdata[k] = new byte[(int) len];
				IO.readFully(fd
			}
		}
		objectCnt = idxHeader[255];

		packChecksum = new byte[20];
		IO.readFully(fd
	}

	@Override
	public long getObjectCount() {
		return objectCnt;
	}

	@Override
	public long getOffset64Count() {
		long n64 = 0;
		for (MutableEntry e : this) {
			if (e.getOffset() >= Integer.MAX_VALUE)
				n64++;
		}
		return n64;
	}

	private int findLevelOne(long nthPosition) {
		int levelOne = Arrays.binarySearch(idxHeader
		if (levelOne >= 0) {
			long base = idxHeader[levelOne];
			while (levelOne > 0 && base == idxHeader[levelOne - 1])
				levelOne--;
		} else {
			levelOne = -(levelOne + 1);
		}
		return levelOne;
	}

	private int getLevelTwo(long nthPosition
		final long base = levelOne > 0 ? idxHeader[levelOne - 1] : 0;
		return (int) (nthPosition - base);
	}

	@Override
	public ObjectId getObjectId(long nthPosition) {
		final int levelOne = findLevelOne(nthPosition);
		final int p = getLevelTwo(nthPosition
		final int dataIdx = idOffset(p);
		return ObjectId.fromRaw(idxdata[levelOne]
	}

	@Override
	long getOffset(long nthPosition) {
		final int levelOne = findLevelOne(nthPosition);
		final int levelTwo = getLevelTwo(nthPosition
		final int p = (4 + Constants.OBJECT_ID_LENGTH) * levelTwo;
		return NB.decodeUInt32(idxdata[levelOne]
	}

	@Override
	public long findOffset(AnyObjectId objId) {
		final int levelOne = objId.getFirstByte();
		byte[] data = idxdata[levelOne];
		if (data == null)
			return -1;
		int high = data.length / (4 + Constants.OBJECT_ID_LENGTH);
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final int pos = idOffset(mid);
			final int cmp = objId.compareTo(data
			if (cmp < 0)
				high = mid;
			else if (cmp == 0) {
				int b0 = data[pos - 4] & 0xff;
				int b1 = data[pos - 3] & 0xff;
				int b2 = data[pos - 2] & 0xff;
				int b3 = data[pos - 1] & 0xff;
				return (((long) b0) << 24) | (b1 << 16) | (b2 << 8) | (b3);
			} else
				low = mid + 1;
		} while (low < high);
		return -1;
	}

	@Override
	public long findCRC32(AnyObjectId objId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasCRC32Support() {
		return false;
	}

	@Override
	public Iterator<MutableEntry> iterator() {
		return new IndexV1Iterator();
	}

	@Override
	public void resolve(Set<ObjectId> matches
			int matchLimit) throws IOException {
		byte[] data = idxdata[id.getFirstByte()];
		if (data == null)
			return;
		int max = data.length / (4 + Constants.OBJECT_ID_LENGTH);
		int high = max;
		int low = 0;
		do {
			int p = (low + high) >>> 1;
			final int cmp = id.prefixCompare(data
			if (cmp < 0)
				high = p;
			else if (cmp == 0) {
				while (0 < p && id.prefixCompare(data
					p--;
				for (; p < max && id.prefixCompare(data
					matches.add(ObjectId.fromRaw(data
					if (matches.size() > matchLimit)
						break;
				}
				return;
			} else
				low = p + 1;
		} while (low < high);
	}

	private static int idOffset(int mid) {
		return ((4 + Constants.OBJECT_ID_LENGTH) * mid) + 4;
	}

	private class IndexV1Iterator extends EntriesIterator {
		int levelOne;

		int levelTwo;

		@Override
		protected MutableEntry initEntry() {
			return new MutableEntry() {
				@Override
				protected void ensureId() {
					idBuffer.fromRaw(idxdata[levelOne]
							- Constants.OBJECT_ID_LENGTH);
				}
			};
		}

		@Override
		public MutableEntry next() {
			for (; levelOne < idxdata.length; levelOne++) {
				if (idxdata[levelOne] == null)
					continue;
				if (levelTwo < idxdata[levelOne].length) {
					entry.offset = NB.decodeUInt32(idxdata[levelOne]
					levelTwo += Constants.OBJECT_ID_LENGTH + 4;
					returnedNumber++;
					return entry;
				}
				levelTwo = 0;
			}
			throw new NoSuchElementException();
		}
	}
}
