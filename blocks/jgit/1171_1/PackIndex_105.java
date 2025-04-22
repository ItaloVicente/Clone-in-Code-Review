
package org.eclipse.jgit.storage.file;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class PackFile implements Iterable<PackIndex.MutableEntry> {
	public static Comparator<PackFile> SORT = new Comparator<PackFile>() {
		public int compare(final PackFile a
			return b.packLastModified - a.packLastModified;
		}
	};

	private final File idxFile;

	private final File packFile;

	final int hash;

	private RandomAccessFile fd;

	private final Object readLock = new Object();

	long length;

	private int activeWindows;

	private int activeCopyRawData;

	private int packLastModified;

	private volatile boolean invalid;

	private byte[] packChecksum;

	private PackIndex loadedIdx;

	private PackReverseIndex reverseIdx;

	private volatile LongList corruptObjects;

	public PackFile(final File idxFile
		this.idxFile = idxFile;
		this.packFile = packFile;
		this.packLastModified = (int) (packFile.lastModified() >> 10);

		hash = System.identityHashCode(this) * 31;
		length = Long.MAX_VALUE;
	}

	private synchronized PackIndex idx() throws IOException {
		if (loadedIdx == null) {
			if (invalid)
				throw new PackInvalidException(packFile);

			try {
				final PackIndex idx = PackIndex.open(idxFile);

				if (packChecksum == null)
					packChecksum = idx.packChecksum;
				else if (!Arrays.equals(packChecksum
					throw new PackMismatchException(JGitText.get().packChecksumMismatch);

				loadedIdx = idx;
			} catch (IOException e) {
				invalid = true;
				throw e;
			}
		}
		return loadedIdx;
	}

	public File getPackFile() {
		return packFile;
	}

	public boolean hasObject(final AnyObjectId id) throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset && !isCorrupt(offset);
	}

	ObjectLoader get(final WindowCursor curs
			throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset && !isCorrupt(offset) ? load(curs
	}

	public void close() {
		UnpackedObjectCache.purge(this);
		WindowCache.purge(this);
		synchronized (this) {
			loadedIdx = null;
			reverseIdx = null;
		}
	}

	public Iterator<PackIndex.MutableEntry> iterator() {
		try {
			return idx().iterator();
		} catch (IOException e) {
			return Collections.<PackIndex.MutableEntry> emptyList().iterator();
		}
	}

	long getObjectCount() throws IOException {
		return idx().getObjectCount();
	}

	ObjectId findObjectForOffset(final long offset) throws IOException {
		return getReverseIdx().findObject(offset);
	}

	private final UnpackedObjectCache.Entry readCache(final long position) {
		return UnpackedObjectCache.get(this
	}

	private final void saveCache(final long position
		UnpackedObjectCache.store(this
	}

	private final byte[] decompress(final long position
			final WindowCursor curs) throws IOException
		final byte[] dstbuf = new byte[(int) totalSize];
		if (curs.inflate(this
			throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt
		return dstbuf;
	}

	final void copyAsIs(PackOutputStream out
			WindowCursor curs) throws IOException
			StoredObjectRepresentationNotAvailableException {
		beginCopyAsIs(src);
		try {
			copyAsIs2(out
		} finally {
			endCopyAsIs();
		}
	}

	private void copyAsIs2(PackOutputStream out
			WindowCursor curs) throws IOException
			StoredObjectRepresentationNotAvailableException {
		final CRC32 crc1 = new CRC32();
		final CRC32 crc2 = new CRC32();
		final byte[] buf = out.getCopyBuffer();

		readFully(src.offset
		int c = buf[0] & 0xff;
		final int typeCode = (c >> 4) & 7;
		long inflatedLength = c & 15;
		int shift = 4;
		int headerCnt = 1;
		while ((c & 0x80) != 0) {
			c = buf[headerCnt++] & 0xff;
			inflatedLength += (c & 0x7f) << shift;
			shift += 7;
		}

		if (typeCode == Constants.OBJ_OFS_DELTA) {
			do {
				c = buf[headerCnt++] & 0xff;
			} while ((c & 128) != 0);
			crc1.update(buf
			crc2.update(buf
		} else if (typeCode == Constants.OBJ_REF_DELTA) {
			crc1.update(buf
			crc2.update(buf

			readFully(src.offset + headerCnt
			crc1.update(buf
			crc2.update(buf
			headerCnt += 20;
		} else {
			crc1.update(buf
			crc2.update(buf
		}

		final long dataOffset = src.offset + headerCnt;
		final long dataLength = src.length;
		final long expectedCRC;
		final ByteArrayWindow quickCopy;

		try {
			quickCopy = curs.quickCopy(this

			if (idx().hasCRC32Support()) {
				expectedCRC = idx().findCRC32(src);
				if (quickCopy != null) {
					quickCopy.crc32(crc1
				} else {
					long pos = dataOffset;
					long cnt = dataLength;
					while (cnt > 0) {
						final int n = (int) Math.min(cnt
						readFully(pos
						crc1.update(buf
						pos += n;
						cnt -= n;
					}
				}
				if (crc1.getValue() != expectedCRC) {
					setCorrupt(src.offset);
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							src.offset
				}
			} else {
				Inflater inf = curs.inflater();
				byte[] tmp = new byte[1024];
				if (quickCopy != null) {
					quickCopy.check(inf
				} else {
					long pos = dataOffset;
					long cnt = dataLength;
					while (cnt > 0) {
						final int n = (int) Math.min(cnt
						readFully(pos
						crc1.update(buf
						inf.setInput(buf
						while (inf.inflate(tmp
							continue;
						pos += n;
						cnt -= n;
					}
				}
				if (!inf.finished() || inf.getBytesRead() != dataLength) {
					setCorrupt(src.offset);
					throw new EOFException(MessageFormat.format(
							JGitText.get().shortCompressedStreamAt
							src.offset));
				}
				expectedCRC = crc1.getValue();
			}
		} catch (DataFormatException dataFormat) {
			setCorrupt(src.offset);

			CorruptObjectException corruptObject = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							src.offset
			corruptObject.initCause(dataFormat);

			StoredObjectRepresentationNotAvailableException gone;
			gone = new StoredObjectRepresentationNotAvailableException(src);
			gone.initCause(corruptObject);
			throw gone;

		} catch (IOException ioError) {
			StoredObjectRepresentationNotAvailableException gone;
			gone = new StoredObjectRepresentationNotAvailableException(src);
			gone.initCause(ioError);
			throw gone;
		}

		if (quickCopy != null) {
			out.writeHeader(src
			quickCopy.write(out

		} else if (dataLength <= buf.length) {
			out.writeHeader(src
			out.write(buf
		} else {
			out.writeHeader(src
			long pos = dataOffset;
			long cnt = dataLength;
			while (cnt > 0) {
				final int n = (int) Math.min(cnt
				readFully(pos
				crc2.update(buf
				out.write(buf
				pos += n;
				cnt -= n;
			}
			if (crc2.getValue() != expectedCRC) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().objectAtHasBadZlibStream
						getPackFile()));
			}
		}
	}

	boolean invalid() {
		return invalid;
	}

	private void readFully(final long position
			int dstoff
			throws IOException {
		if (curs.copy(this
			throw new EOFException();
	}

	private synchronized void beginCopyAsIs(ObjectToPack otp)
			throws StoredObjectRepresentationNotAvailableException {
		if (++activeCopyRawData == 1 && activeWindows == 0) {
			try {
				doOpen();
			} catch (IOException thisPackNotValid) {
				StoredObjectRepresentationNotAvailableException gone;

				gone = new StoredObjectRepresentationNotAvailableException(otp);
				gone.initCause(thisPackNotValid);
				throw gone;
			}
		}
	}

	private synchronized void endCopyAsIs() {
		if (--activeCopyRawData == 0 && activeWindows == 0)
			doClose();
	}

	synchronized boolean beginWindowCache() throws IOException {
		if (++activeWindows == 1) {
			if (activeCopyRawData == 0)
				doOpen();
			return true;
		}
		return false;
	}

	synchronized boolean endWindowCache() {
		final boolean r = --activeWindows == 0;
		if (r && activeCopyRawData == 0)
			doClose();
		return r;
	}

	private void doOpen() throws IOException {
		try {
			if (invalid)
				throw new PackInvalidException(packFile);
			synchronized (readLock) {
				fd = new RandomAccessFile(packFile
				length = fd.length();
				onOpenPack();
			}
		} catch (IOException ioe) {
			openFail();
			throw ioe;
		} catch (RuntimeException re) {
			openFail();
			throw re;
		} catch (Error re) {
			openFail();
			throw re;
		}
	}

	private void openFail() {
		activeWindows = 0;
		activeCopyRawData = 0;
		invalid = true;
		doClose();
	}

	private void doClose() {
		synchronized (readLock) {
			if (fd != null) {
				try {
					fd.close();
				} catch (IOException err) {
				}
				fd = null;
			}
		}
	}

	ByteArrayWindow read(final long pos
		synchronized (readLock) {
			if (length < pos + size)
				size = (int) (length - pos);
			final byte[] buf = new byte[size];
			fd.seek(pos);
			fd.readFully(buf
			return new ByteArrayWindow(this
		}
	}

	ByteWindow mmap(final long pos
		synchronized (readLock) {
			if (length < pos + size)
				size = (int) (length - pos);

			MappedByteBuffer map;
			try {
				map = fd.getChannel().map(MapMode.READ_ONLY
			} catch (IOException ioe1) {
				System.gc();
				System.runFinalization();
				map = fd.getChannel().map(MapMode.READ_ONLY
			}

			if (map.hasArray())
				return new ByteArrayWindow(this
			return new ByteBufferWindow(this
		}
	}

	private void onOpenPack() throws IOException {
		final PackIndex idx = idx();
		final byte[] buf = new byte[20];

		fd.seek(0);
		fd.readFully(buf
		if (RawParseUtils.match(buf
			throw new IOException(JGitText.get().notAPACKFile);
		final long vers = NB.decodeUInt32(buf
		final long packCnt = NB.decodeUInt32(buf
		if (vers != 2 && vers != 3)
			throw new IOException(MessageFormat.format(JGitText.get().unsupportedPackVersion

		if (packCnt != idx.getObjectCount())
			throw new PackMismatchException(MessageFormat.format(
					JGitText.get().packObjectCountMismatch

		fd.seek(length - 20);
		fd.read(buf
		if (!Arrays.equals(buf
			throw new PackMismatchException(MessageFormat.format(
					JGitText.get().packObjectCountMismatch
					
					
					
	}

	ObjectLoader load(final WindowCursor curs
			throws IOException {
		final byte[] ib = curs.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		final int type = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		int p = 1;
		while ((c & 0x80) != 0) {
			c = ib[p++] & 0xff;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

		try {
			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG: {
				if (sz < curs.getStreamFileThreshold()) {
					byte[] data = decompress(pos + p
					return new ObjectLoader.SmallObject(type
				}
				return new LargePackedWholeObject(type
			}

			case Constants.OBJ_OFS_DELTA: {
				c = ib[p++] & 0xff;
				long ofs = c & 127;
				while ((c & 128) != 0) {
					ofs += 1;
					c = ib[p++] & 0xff;
					ofs <<= 7;
					ofs += (c & 127);
				}
				return loadDelta(pos
			}

			case Constants.OBJ_REF_DELTA: {
				readFully(pos + p
				long ofs = findDeltaBase(ObjectId.fromRaw(ib));
				return loadDelta(pos
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		} catch (DataFormatException dfe) {
			CorruptObjectException coe = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getPackFile()));
			coe.initCause(dfe);
			throw coe;
		}
	}

	private long findDeltaBase(ObjectId baseId) throws IOException
			MissingObjectException {
		long ofs = idx().findOffset(baseId);
		if (ofs < 0)
			throw new MissingObjectException(baseId
					JGitText.get().missingDeltaBase);
		return ofs;
	}

	private ObjectLoader loadDelta(long posSelf
			long posBase
			DataFormatException {
		if (curs.getStreamFileThreshold() <= sz) {
			return new LargePackedDeltaObject(posSelf
					this
		}

		byte[] data;
		int type;

		UnpackedObjectCache.Entry e = readCache(posBase);
		if (e != null) {
			data = e.data;
			type = e.type;
		} else {
			ObjectLoader p = load(curs
			if (p.isLarge()) {
				return new LargePackedDeltaObject(posSelf
						this
			}
			data = p.getCachedBytes();
			type = p.getType();
			saveCache(posBase
		}

		data = BinaryDelta.apply(data
		return new ObjectLoader.SmallObject(type
	}

	byte[] getDeltaHeader(WindowCursor wc
			throws IOException
		final byte[] hdr = new byte[18];
		wc.inflate(this
		return hdr;
	}

	int getObjectType(final WindowCursor curs
		final byte[] ib = curs.tempId;
		for (;;) {
			readFully(pos
			int c = ib[0] & 0xff;
			final int type = (c >> 4) & 7;
			int shift = 4;
			int p = 1;
			while ((c & 0x80) != 0) {
				c = ib[p++] & 0xff;
				shift += 7;
			}

			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				return type;

			case Constants.OBJ_OFS_DELTA: {
				c = ib[p++] & 0xff;
				long ofs = c & 127;
				while ((c & 128) != 0) {
					ofs += 1;
					c = ib[p++] & 0xff;
					ofs <<= 7;
					ofs += (c & 127);
				}
				pos = pos - ofs;
				continue;
			}

			case Constants.OBJ_REF_DELTA: {
				readFully(pos + p
				pos = findDeltaBase(ObjectId.fromRaw(ib));
				continue;
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		}
	}

	long getObjectSize(final WindowCursor curs
			throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset ? getObjectSize(curs
	}

	long getObjectSize(final WindowCursor curs
			throws IOException {
		final byte[] ib = curs.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		final int type = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		int p = 1;
		while ((c & 0x80) != 0) {
			c = ib[p++] & 0xff;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

		long deltaAt;
		switch (type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			return sz;

		case Constants.OBJ_OFS_DELTA:
			c = ib[p++] & 0xff;
			while ((c & 128) != 0)
				c = ib[p++] & 0xff;
			deltaAt = pos + p;
			break;

		case Constants.OBJ_REF_DELTA:
			deltaAt = pos + p + 20;
			break;

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}

		try {
			return BinaryDelta.getResultSize(getDeltaHeader(curs
		} catch (DataFormatException e) {
			throw new CorruptObjectException(MessageFormat.format(JGitText
					.get().objectAtHasBadZlibStream
		}
	}

	LocalObjectRepresentation representation(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		final long pos = idx().findOffset(objectId);
		if (pos < 0)
			return null;

		final byte[] ib = curs.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		int p = 1;
		final int typeCode = (c >> 4) & 7;
		while ((c & 0x80) != 0)
			c = ib[p++] & 0xff;

		long len = (findEndOffset(pos) - pos);
		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			return LocalObjectRepresentation.newWhole(this

		case Constants.OBJ_OFS_DELTA: {
			c = ib[p++] & 0xff;
			long ofs = c & 127;
			while ((c & 128) != 0) {
				ofs += 1;
				c = ib[p++] & 0xff;
				ofs <<= 7;
				ofs += (c & 127);
			}
			ofs = pos - ofs;
			return LocalObjectRepresentation.newDelta(this
		}

		case Constants.OBJ_REF_DELTA: {
			len -= p;
			len -= Constants.OBJECT_ID_LENGTH;
			readFully(pos + p
			ObjectId id = ObjectId.fromRaw(ib);
			return LocalObjectRepresentation.newDelta(this
		}

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
	}

	private long findEndOffset(final long startOffset)
			throws IOException
		final long maxOffset = length - 20;
		return getReverseIdx().findNextOffset(startOffset
	}

	private synchronized PackReverseIndex getReverseIdx() throws IOException {
		if (reverseIdx == null)
			reverseIdx = new PackReverseIndex(idx());
		return reverseIdx;
	}

	private boolean isCorrupt(long offset) {
		LongList list = corruptObjects;
		if (list == null)
			return false;
		synchronized (list) {
			return list.contains(offset);
		}
	}

	private void setCorrupt(long offset) {
		LongList list = corruptObjects;
		if (list == null) {
			synchronized (readLock) {
				list = corruptObjects;
				if (list == null) {
					list = new LongList();
					corruptObjects = list;
				}
			}
		}
		synchronized (list) {
			list.add(offset);
		}
	}
}
