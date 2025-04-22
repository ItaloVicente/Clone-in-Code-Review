
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.internal.storage.pack.PackExt.BITMAP_INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.KEEP;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoPackSignatureException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.errors.UnsupportedPackIndexVersionException;
import org.eclipse.jgit.errors.UnsupportedPackVersionException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.BinaryDelta;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class PackFile implements Iterable<PackIndex.MutableEntry> {
	public static final Comparator<PackFile> SORT = new Comparator<PackFile>() {
		@Override
		public int compare(PackFile a
			return b.packLastModified - a.packLastModified;
		}
	};

	private final File packFile;

	private final int extensions;

	private File keepFile;

	private volatile String packName;

	final int hash;

	private RandomAccessFile fd;

	private final Object readLock = new Object();

	long length;

	private int activeWindows;

	private int activeCopyRawData;

	int packLastModified;

	private FileSnapshot fileSnapshot;

	private volatile boolean invalid;

	private boolean invalidBitmap;

	private AtomicInteger transientErrorCount = new AtomicInteger();

	private byte[] packChecksum;

	private volatile PackIndex loadedIdx;

	private PackReverseIndex reverseIdx;

	private PackBitmapIndex bitmapIdx;

	private volatile LongList corruptObjects;

	public PackFile(File packFile
		this.packFile = packFile;
		this.fileSnapshot = FileSnapshot.save(packFile);
		this.packLastModified = (int) (fileSnapshot.lastModified() >> 10);
		this.extensions = extensions;

		hash = System.identityHashCode(this) * 31;
		length = Long.MAX_VALUE;
	}

	private PackIndex idx() throws IOException {
		PackIndex idx = loadedIdx;
		if (idx == null) {
			synchronized (this) {
				idx = loadedIdx;
				if (idx == null) {
					if (invalid) {
						throw new PackInvalidException(packFile);
					}
					try {
						idx = PackIndex.open(extFile(INDEX));

						if (packChecksum == null) {
							packChecksum = idx.packChecksum;
						} else if (!Arrays.equals(packChecksum
								idx.packChecksum)) {
							throw new PackMismatchException(MessageFormat
									.format(JGitText.get().packChecksumMismatch
											packFile.getPath()
											ObjectId.fromRaw(packChecksum)
													.name()
											ObjectId.fromRaw(idx.packChecksum)
													.name()));
						}
						loadedIdx = idx;
					} catch (InterruptedIOException e) {
						throw e;
					} catch (IOException e) {
						invalid = true;
						throw e;
					}
				}
			}
		}
		return idx;
	}
	public File getPackFile() {
		return packFile;
	}

	public PackIndex getIndex() throws IOException {
		return idx();
	}

	public String getPackName() {
		String name = packName;
		if (name == null) {
			name = getPackFile().getName();
				name = name.substring(0
			packName = name;
		}
		return name;
	}

	public boolean hasObject(AnyObjectId id) throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset && !isCorrupt(offset);
	}

	public boolean shouldBeKept() {
		if (keepFile == null)
			keepFile = extFile(KEEP);
		return keepFile.exists();
	}

	ObjectLoader get(WindowCursor curs
			throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset && !isCorrupt(offset) ? load(curs
	}

	void resolve(Set<ObjectId> matches
			throws IOException {
		idx().resolve(matches
	}

	public void close() {
		WindowCache.purge(this);
		synchronized (this) {
			loadedIdx = null;
			reverseIdx = null;
		}
	}

	@Override
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

	ObjectId findObjectForOffset(long offset) throws IOException {
		return getReverseIdx().findObject(offset);
	}

	FileSnapshot getFileSnapshot() {
		return fileSnapshot;
	}

	private final byte[] decompress(final long position
			final WindowCursor curs) throws IOException
		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}

		if (curs.inflate(this
			throw new EOFException(MessageFormat.format(
					JGitText.get().shortCompressedStreamAt
					Long.valueOf(position)));
		return dstbuf;
	}

	void copyPackAsIs(PackOutputStream out
			throws IOException {
		curs.pin(this
		curs.copyPackAsIs(this
	}

	final void copyAsIs(PackOutputStream out
			boolean validate
			StoredObjectRepresentationNotAvailableException {
		beginCopyAsIs(src);
		try {
			copyAsIs2(out
		} finally {
			endCopyAsIs();
		}
	}

	private void copyAsIs2(PackOutputStream out
			boolean validate
			StoredObjectRepresentationNotAvailableException {
		final CRC32 crc1 = validate ? new CRC32() : null;
		final CRC32 crc2 = validate ? new CRC32() : null;
		final byte[] buf = out.getCopyBuffer();

		readFully(src.offset
		int c = buf[0] & 0xff;
		final int typeCode = (c >> 4) & 7;
		long inflatedLength = c & 15;
		int shift = 4;
		int headerCnt = 1;
		while ((c & 0x80) != 0) {
			c = buf[headerCnt++] & 0xff;
			inflatedLength += ((long) (c & 0x7f)) << shift;
			shift += 7;
		}

		if (typeCode == Constants.OBJ_OFS_DELTA) {
			do {
				c = buf[headerCnt++] & 0xff;
			} while ((c & 128) != 0);
			if (validate) {
				assert(crc1 != null && crc2 != null);
				crc1.update(buf
				crc2.update(buf
			}
		} else if (typeCode == Constants.OBJ_REF_DELTA) {
			if (validate) {
				assert(crc1 != null && crc2 != null);
				crc1.update(buf
				crc2.update(buf
			}

			readFully(src.offset + headerCnt
			if (validate) {
				assert(crc1 != null && crc2 != null);
				crc1.update(buf
				crc2.update(buf
			}
			headerCnt += 20;
		} else if (validate) {
			assert(crc1 != null && crc2 != null);
			crc1.update(buf
			crc2.update(buf
		}

		final long dataOffset = src.offset + headerCnt;
		final long dataLength = src.length;
		final long expectedCRC;
		final ByteArrayWindow quickCopy;

		try {
			quickCopy = curs.quickCopy(this

			if (validate && idx().hasCRC32Support()) {
				assert(crc1 != null);
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
							Long.valueOf(src.offset)
				}
			} else if (validate) {
				Inflater inf = curs.inflater();
				byte[] tmp = new byte[1024];
				if (quickCopy != null) {
					quickCopy.check(inf
				} else {
					assert(crc1 != null);
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
							Long.valueOf(src.offset)));
				}
				assert(crc1 != null);
				expectedCRC = crc1.getValue();
			} else {
				expectedCRC = -1;
			}
		} catch (DataFormatException dataFormat) {
			setCorrupt(src.offset);

			CorruptObjectException corruptObject = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							Long.valueOf(src.offset)
					dataFormat);

			throw new StoredObjectRepresentationNotAvailableException(src
					corruptObject);

		} catch (IOException ioError) {
			throw new StoredObjectRepresentationNotAvailableException(src
					ioError);
		}

		if (quickCopy != null) {
			out.writeHeader(src
			quickCopy.write(out

		} else if (dataLength <= buf.length) {
			if (!validate) {
				long pos = dataOffset;
				long cnt = dataLength;
				while (cnt > 0) {
					final int n = (int) Math.min(cnt
					readFully(pos
					pos += n;
					cnt -= n;
				}
			}
			out.writeHeader(src
			out.write(buf
		} else {
			out.writeHeader(src
			long pos = dataOffset;
			long cnt = dataLength;
			while (cnt > 0) {
				final int n = (int) Math.min(cnt
				readFully(pos
				if (validate) {
					assert(crc2 != null);
					crc2.update(buf
				}
				out.write(buf
				pos += n;
				cnt -= n;
			}
			if (validate) {
				assert(crc2 != null);
				if (crc2.getValue() != expectedCRC) {
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							Long.valueOf(src.offset)
				}
			}
		}
	}

	boolean invalid() {
		return invalid;
	}

	void setInvalid() {
		invalid = true;
	}

	int incrementTransientErrorCount() {
		return transientErrorCount.incrementAndGet();
	}

	void resetTransientErrorCount() {
		transientErrorCount.set(0);
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
				throw new StoredObjectRepresentationNotAvailableException(otp
						thisPackNotValid);
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
		if (invalid) {
			throw new PackInvalidException(packFile);
		}
		try {
			synchronized (readLock) {
				fd = new RandomAccessFile(packFile
				length = fd.length();
				onOpenPack();
			}
		} catch (InterruptedIOException e) {
			openFail(false);
			throw e;
		} catch (FileNotFoundException fn) {
			openFail(!packFile.exists());
			throw fn;
		} catch (EOFException | AccessDeniedException | NoSuchFileException
				| CorruptObjectException | NoPackSignatureException
				| PackMismatchException | UnpackException
				| UnsupportedPackIndexVersionException
				| UnsupportedPackVersionException pe) {
			openFail(true);
			throw pe;
		} catch (IOException | RuntimeException ge) {
			openFail(false);
			throw ge;
		}
	}

	private void openFail(boolean invalidate) {
		activeWindows = 0;
		activeCopyRawData = 0;
		invalid = invalidate;
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

	ByteArrayWindow read(long pos
		synchronized (readLock) {
			if (invalid || fd == null) {
				throw new PackInvalidException(packFile);
			}
			if (length < pos + size)
				size = (int) (length - pos);
			final byte[] buf = new byte[size];
			fd.seek(pos);
			fd.readFully(buf
			return new ByteArrayWindow(this
		}
	}

	ByteWindow mmap(long pos
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
			throw new NoPackSignatureException(JGitText.get().notAPACKFile);
		}
		final long vers = NB.decodeUInt32(buf
		final long packCnt = NB.decodeUInt32(buf
		if (vers != 2 && vers != 3) {
			throw new UnsupportedPackVersionException(vers);
		}

		if (packCnt != idx.getObjectCount()) {
			throw new PackMismatchException(MessageFormat.format(
					JGitText.get().packObjectCountMismatch
					Long.valueOf(packCnt)
					getPackFile()));
		}

		fd.seek(length - 20);
		fd.readFully(buf
		if (!Arrays.equals(buf
			throw new PackMismatchException(MessageFormat.format(
					JGitText.get().packChecksumMismatch
					getPackFile()
					ObjectId.fromRaw(buf).name()
					ObjectId.fromRaw(idx.packChecksum).name()));
		}
	}

	ObjectLoader load(WindowCursor curs
			throws IOException
		try {
			final byte[] ib = curs.tempId;
			Delta delta = null;
			byte[] data = null;
			int type = Constants.OBJ_BAD;
			boolean cached = false;

			SEARCH: for (;;) {
				readFully(pos
				int c = ib[0] & 0xff;
				final int typeCode = (c >> 4) & 7;
				long sz = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = ib[p++] & 0xff;
					sz += ((long) (c & 0x7f)) << shift;
					shift += 7;
				}

				switch (typeCode) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG: {
					if (delta != null || sz < curs.getStreamFileThreshold())
						data = decompress(pos + p

					if (delta != null) {
						type = typeCode;
						break SEARCH;
					}

					if (data != null)
						return new ObjectLoader.SmallObject(typeCode
					else
						return new LargePackedWholeObject(typeCode
								this
				}

				case Constants.OBJ_OFS_DELTA: {
					c = ib[p++] & 0xff;
					long base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = ib[p++] & 0xff;
						base <<= 7;
						base += (c & 127);
					}
					base = pos - base;
					delta = new Delta(delta
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = curs.getDeltaBaseCache().get(this
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pos = base;
					continue SEARCH;
				}

				case Constants.OBJ_REF_DELTA: {
					readFully(pos + p
					long base = findDeltaBase(ObjectId.fromRaw(ib));
					delta = new Delta(delta
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = curs.getDeltaBaseCache().get(this
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pos = base;
					continue SEARCH;
				}

				default:
					throw new IOException(MessageFormat.format(
							JGitText.get().unknownObjectType
							Integer.valueOf(typeCode)));
				}
			}


			if (data == null)
				throw new IOException(JGitText.get().inMemoryBufferLimitExceeded);

			assert(delta != null);
			do {
				if (cached)
					cached = false;
				else if (delta.next == null)
					curs.getDeltaBaseCache().store(this

				pos = delta.deltaPos;

				final byte[] cmds = decompress(pos + delta.hdrLen
						delta.deltaSize
				if (cmds == null) {
					throw new LargeObjectException.OutOfMemory(new OutOfMemoryError());
				}

				final long sz = BinaryDelta.getResultSize(cmds);
				if (Integer.MAX_VALUE <= sz)
					throw new LargeObjectException.ExceedsByteArrayLimit();

				final byte[] result;
				try {
					result = new byte[(int) sz];
				} catch (OutOfMemoryError tooBig) {
					throw new LargeObjectException.OutOfMemory(tooBig);
				}

				BinaryDelta.apply(data
				data = result;
				delta = delta.next;
			} while (delta != null);

			return new ObjectLoader.SmallObject(type

		} catch (DataFormatException dfe) {
			throw new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							Long.valueOf(pos)
					dfe);
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

	private static class Delta {
		final Delta next;

		final long deltaPos;

		final int deltaSize;

		final int hdrLen;

		final long basePos;

		Delta(Delta next
			this.next = next;
			this.deltaPos = ofs;
			this.deltaSize = sz;
			this.hdrLen = hdrLen;
			this.basePos = baseOffset;
		}
	}

	byte[] getDeltaHeader(WindowCursor wc
			throws IOException
		final byte[] hdr = new byte[18];
		wc.inflate(this
		return hdr;
	}

	int getObjectType(WindowCursor curs
		final byte[] ib = curs.tempId;
		for (;;) {
			readFully(pos
			int c = ib[0] & 0xff;
			final int type = (c >> 4) & 7;

			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				return type;

			case Constants.OBJ_OFS_DELTA: {
				int p = 1;
				while ((c & 0x80) != 0)
					c = ib[p++] & 0xff;
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
				int p = 1;
				while ((c & 0x80) != 0)
					c = ib[p++] & 0xff;
				readFully(pos + p
				pos = findDeltaBase(ObjectId.fromRaw(ib));
				continue;
			}

			default:
				throw new IOException(
						MessageFormat.format(JGitText.get().unknownObjectType
								Integer.valueOf(type)));
			}
		}
	}

	long getObjectSize(WindowCursor curs
			throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset ? getObjectSize(curs
	}

	long getObjectSize(WindowCursor curs
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
			sz += ((long) (c & 0x7f)) << shift;
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
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().objectAtHasBadZlibStream
					getPackFile()));
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
			throw new IOException(
					MessageFormat.format(JGitText.get().unknownObjectType
							Integer.valueOf(typeCode)));
		}
	}

	private long findEndOffset(long startOffset)
			throws IOException
		final long maxOffset = length - 20;
		return getReverseIdx().findNextOffset(startOffset
	}

	synchronized PackBitmapIndex getBitmapIndex() throws IOException {
		if (invalid || invalidBitmap)
			return null;
		if (bitmapIdx == null && hasExt(BITMAP_INDEX)) {
			final PackBitmapIndex idx;
			try {
				idx = PackBitmapIndex.open(extFile(BITMAP_INDEX)
						getReverseIdx());
			} catch (FileNotFoundException e) {
				 invalidBitmap = true;
				 return null;
			}

			if (Arrays.equals(packChecksum
				bitmapIdx = idx;
			else
				invalidBitmap = true;
		}
		return bitmapIdx;
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

	private File extFile(PackExt ext) {
		String p = packFile.getName();
		int dot = p.lastIndexOf('.');
		String b = (dot < 0) ? p : p.substring(0
		return new File(packFile.getParentFile()
	}

	private boolean hasExt(PackExt ext) {
		return (extensions & ext.getBit()) != 0;
	}
}
