
package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.ReadableChannel;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.internal.storage.pack.CorruptPackIndexException.ErrorType;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class FsckPackParser extends PackParser {
	private final ObjectChecker objCheck;

	private final CRC32 crc;

	private final ReadableChannel channel;

	private final Set<CorruptObject> corruptObjects = new HashSet<>();

	private boolean overwriteObjectCount;

	private long expectedObjectCount;

	private long offset;

	private int blockSize;

	public FsckPackParser(Repository git
			ObjectChecker objCheck
		super(git.getObjectDatabase()
		this.channel = channel;
		setCheckObjectCollisions(false);
		this.objCheck = objCheck;
		setObjectChecker(objCheck);
		this.crc = new CRC32();
		this.overwriteObjectCount = true;
		this.expectedObjectCount = expectedObjectCount;
		this.blockSize = channel.blockSize() > 0 ? channel.blockSize() : 65536;
	}

	@Override
	protected void onPackHeader(long objCnt) throws IOException {
		if (overwriteObjectCount) {
			setExpectedObjectCount(expectedObjectCount);
		}
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		crc.reset();
	}

	@Override
	protected void onObjectHeader(Source src
			throws IOException {
		crc.update(raw
	}

	@Override
	protected void onObjectData(Source src
			throws IOException {
		crc.update(raw
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		info.setCRC((int) crc.getValue());
	}

	@Override
	protected void onBeginOfsDelta(long deltaStreamPosition
			long baseStreamPosition
		crc.reset();
	}

	@Override
	protected void onBeginRefDelta(long deltaStreamPosition
			long inflatedSize) throws IOException {
		crc.reset();
	}

	@Override
	protected UnresolvedDelta onEndDelta() throws IOException {
		UnresolvedDelta delta = new UnresolvedDelta();
		delta.setCRC((int) crc.getValue());
		return delta;
	}

	@Override
	protected void onInflatedObjectData(PackedObjectInfo obj
			byte[] data) throws IOException {
	}

	@Override
	protected void verifySafeObject(final AnyObjectId id
			final byte[] data) {
		if (objCheck != null) {
			try {
				objCheck.check(id
			} catch (CorruptObjectException e) {
				CorruptObject o = new CorruptObject(id.toObjectId()
				if (e.getErrorType() != null) {
					o.setErrorType(e.getErrorType());
				}
				corruptObjects.add(o);
			}
		}
	}

	@Override
	protected void onPackFooter(byte[] hash) throws IOException {
	}

	@Override
	protected boolean onAppendBase(int typeCode
			PackedObjectInfo info) throws IOException {
		return false;
	}

	@Override
	protected void onEndThinPack() throws IOException {
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		crc.reset();
		offset = obj.getOffset();
		return readObjectHeader(info);
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		crc.reset();
		offset = delta.getOffset();
		return readObjectHeader(info);
	}

	@Override
	protected int readDatabase(byte[] dst
			throws IOException {
		int n = read(offset
		if (n > 0) {
			offset += n;
		}
		return n;
	}

	int read(long channelPosition
			throws IOException {
		long block = channelPosition / blockSize;
		byte[] bytes = readFromChannel(block);
		if (bytes == null) {
			return -1;
		}
		int offset = (int) (channelPosition - block * blockSize);
		int bytesToCopy = Math.min(cnt
		if (bytesToCopy < 1) {
			return -1;
		}
		System.arraycopy(bytes
		return bytesToCopy;
	}

	private byte[] readFromChannel(long block) throws IOException {
		channel.position(block * blockSize);
		ByteBuffer buf = ByteBuffer.allocate(blockSize);
		int totalBytesRead = 0;
		while (totalBytesRead < blockSize) {
			int bytesRead = channel.read(buf);
			if (bytesRead == -1) {
				if (totalBytesRead == 0) {
					return null;
				}
				return Arrays.copyOf(buf.array()
			}
			totalBytesRead += bytesRead;
		}
		return buf.array();
	}

	@Override
	protected boolean checkCRC(int oldCRC) {
		return oldCRC == (int) crc.getValue();
	}

	@Override
	protected void onStoreStream(byte[] raw
			throws IOException {
	}

	public Set<CorruptObject> getCorruptObjects() {
		return corruptObjects;
	}

	public void verifyIndex(List<PackedObjectInfo> entries
			throws CorruptPackIndexException {
		Set<String> all = new HashSet<>();
		for (PackedObjectInfo entry : entries) {
			all.add(entry.getName());
			long offset = idx.findOffset(entry);
			if (offset == -1) {
				throw new CorruptPackIndexException(
						MessageFormat.format(JGitText.get().missingObject
								entry.getType()
						ErrorType.MISSING_OBJ);
			} else if (offset != entry.getOffset()) {
				throw new CorruptPackIndexException(
						MessageFormat
								.format(JGitText.get().mismatchOffset
										entry.getName())
						ErrorType.MISMATCH_OFFSET);
			}

			try {
				if (idx.hasCRC32Support() && (int) idx.findCRC32(entry) != entry
						.getCRC()) {
					throw new CorruptPackIndexException(
							MessageFormat
									.format(JGitText.get().mismatchCRC
											entry.getName())
							ErrorType.MISMATCH_CRC);
				}
			} catch (MissingObjectException e) {
				throw new CorruptPackIndexException(
						MessageFormat.format(JGitText.get().missingCRC
								entry.getName())
						ErrorType.MISSING_CRC);
			}
		}

		for (MutableEntry entry : idx) {
			if (!all.contains(entry.name())) {
				throw new CorruptPackIndexException(
						MessageFormat
								.format(JGitText.get().unknownObjectInIndex
										entry.name())
						ErrorType.UNKNOWN_OBJ);
			}
		}
	}

	public static class CorruptObject {
		final ObjectId id;

		final int type;

		ObjectChecker.ErrorType errorType;

		public CorruptObject(ObjectId id
			this.id = id;
			this.type = type;
		}

		void setErrorType(ObjectChecker.ErrorType errorType) {
			this.errorType = errorType;
		}

		public ObjectId getId() {
			return id;
		}

		public int getType() {
			return type;
		}

		public ObjectChecker.ErrorType getErrorType() {
			return errorType;
		}
	}

	public static class CorruptIndex {
		String packName;
		CorruptPackIndexException.ErrorType errorType;

		public CorruptIndex(String packName
				ErrorType errorType) {
			this.packName = packName;
			this.errorType = errorType;
		}

		public String getPackName() {
			return packName;
		}

		public ErrorType getErrorType() {
			return errorType;
		}
	}
}

