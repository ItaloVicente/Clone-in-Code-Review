
package org.eclipse.jgit.internal.storage.file;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

import org.eclipse.jgit.errors.*;
import org.eclipse.jgit.lib.*;

class PackIndexV2m extends PackIndex {
	private static final long IS_O64 = 1L << 31;

	private static final int FANOUT = 256;

	private final byte[] idBuf = new byte[Constants.OBJECT_ID_LENGTH];
	private final Thread ownerThread;
	private final long objectCnt;
	private final long namesOffset;
	private final long crc32Offset;
	private final long offsets32Offset;
	private final long offsets64Offset;
	private final long hashOffset;
	private final long[] fanoutTable;

	private FileChannel channel;
	private RandomAccessFile raFile;
	private MappedByteBuffer buffer;

	PackIndexV2m(File file) throws IOException {
		ownerThread = Thread.currentThread();
		raFile = new RandomAccessFile(file
		channel = raFile.getChannel();
		buffer = channel.map(FileChannel.MapMode.READ_ONLY

		fanoutTable = new long[FANOUT];
		for (int k = 0; k < FANOUT; k++)
			fanoutTable[k] = Integer.toUnsignedLong(buffer.getInt());
		objectCnt = fanoutTable[FANOUT - 1];
		packChecksum = new byte[20];
		namesOffset = 1032;
		crc32Offset = namesOffset + objectCnt * Constants.OBJECT_ID_LENGTH;
		offsets32Offset = crc32Offset + objectCnt * 4;
		offsets64Offset = offsets32Offset + objectCnt * 4;
		hashOffset = (int)raFile.length() - 40;
		buffer.position((int)hashOffset);
		buffer.get(packChecksum
	}

	@Override
	public long getObjectCount() {
		return objectCnt;
	}

	@Override
	public long getOffset64Count() {
		return (hashOffset - offsets64Offset) / 8;
	}

	@Override
	public ObjectId getObjectId(long nthPosition) {
		assertThread();

		return ObjectId.fromRaw(readNameShared(nthPosition));
	}

	@Override
	public long getOffset(long nthPosition) {
		assertThread();

		final long offset32 = Integer.toUnsignedLong(buffer.getInt(toBufferPos(offsets32Offset
		if ((offset32 & IS_O64) == 0) {
			return offset32;
		}

		return buffer.getLong(toBufferPos(offsets64Offset
	}

	@Override
	public long findOffset(AnyObjectId objId) {
		assertThread();

		final long objIndex = findObjectIndex(objId);
		if (objIndex == -1) {
			return -1;
		}

		return getOffset(objIndex);
	}

	@Override
	public long findCRC32(AnyObjectId objId) throws MissingObjectException {
		assertThread();

		final long objIndex = findObjectIndex(objId);
		if (objIndex == -1) {
			throw new MissingObjectException(objId.copy()
		}

		return Integer.toUnsignedLong(buffer.getInt(toBufferPos(crc32Offset
	}

	@Override
	public boolean hasCRC32Support() {
		return true;
	}

	@Override
	public Iterator<MutableEntry> iterator() {
		assertThread();

		return new EntriesIteratorV2();
	}

	@Override
	public void resolve(Set<ObjectId> matches
			int matchLimit) throws IOException {
		final int levelOne = id.getFirstByte();
		final long start = levelOne > 0 ? fanoutTable[levelOne - 1] : 0;
		final long end = fanoutTable[levelOne];
		final long bucketCnt = end - start;
		int high = (int)bucketCnt;
		if (high == 0)
			return;
		int low = 0;
		do {
			int mid = (low + high) >>> 1;
			final int cmp = id.prefixCompare(readNameShared(start + mid)
			if (cmp < 0)
				high = mid;
			else if (cmp == 0) {
				while (0 < mid && id.prefixCompare(readNameShared(start + mid - 1)
					mid--;
				for (; mid < bucketCnt && id.prefixCompare(readNameShared(start + mid)
					matches.add(ObjectId.fromRaw(readNameShared(start + mid)));
					if (matches.size() > matchLimit)
						break;
				}
				return;
			} else
				low = mid + 1;
		} while (low < high);
	}

	@Override
	public void close() {
		synchronized (this) {
			if (channel != null) {
				buffer = null;
				try {
					channel.close();
				}
				catch (IOException ignore) {
				}
				channel = null;
			}
			if (raFile != null) {
				try {
					raFile.close();
				}
				catch (IOException ignore) {
				}
				raFile = null;
			}
			if (buffer != null) {
				buffer = null;
			}
		}
	}

	private long findObjectIndex(AnyObjectId objId) {
		final int levelOne = objId.getFirstByte();
		final int levelTwo = binarySearchLevelTwo(objId
		if (levelTwo == -1) {
			return -1;
		}

		return (levelOne > 0 ? fanoutTable[levelOne - 1] : 0) + levelTwo;
	}

	private int toBufferPos(long offset
		final long pos = offset + objectIndex * rowSize;
		if (pos > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Buffer position " + pos + " is too large for JGit.");
		}
		return (int)pos;
	}

	private int binarySearchLevelTwo(AnyObjectId objId
		final long start = levelOne > 0 ? fanoutTable[levelOne - 1] : 0;
		final long end = fanoutTable[levelOne];
		final long bucketCnt = end - start;
		int high = (int)bucketCnt;
		if (high == 0)
			return -1;
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final int cmp = objId.compareTo(readNameShared(start + mid)
			if (cmp < 0)
				high = mid;
			else if (cmp == 0) {
				return mid;
			} else
				low = mid + 1;
		} while (low < high);
		return -1;
	}

	private byte[] readNameShared(long objectIndex) {
		buffer.position(toBufferPos(namesOffset
		buffer.get(idBuf);
		return idBuf;
	}

	private void assertThread() {
		if (Thread.currentThread() != ownerThread) {
			throw new AssertionError("bad thread access (current=" + Thread.currentThread() + "
		}
	}

	private class EntriesIteratorV2 extends EntriesIterator {
		private long objIndex = -1;

		@Override
		protected MutableEntry initEntry() {
			return new MutableEntry() {
				@Override
				protected void ensureId() {
					idBuffer.fromRaw(readNameShared(objIndex));
				}
			};
		}

		@Override
		public MutableEntry next() {
			objIndex++;
			if (objIndex < objectCnt) {
				entry.offset = getOffset(objIndex);
				returnedNumber++;
				return entry;
			}
			throw new NoSuchElementException();
		}
	}
}
