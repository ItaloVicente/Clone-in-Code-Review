
package org.eclipse.jgit.storage.dfs;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.file.PackReverseIndex;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.LongList;

public final class DfsPackFile {
	private static final long POS_INDEX = -1;

	private static final long POS_REVERSE_INDEX = -2;

	private final DfsBlockCache cache;

	private final DfsPackDescription packDesc;

	final DfsPackKey key;

	volatile long length;

	private volatile int blockSize;

	private volatile boolean invalid;

	private final Object initLock = new Object();

	private volatile DfsBlockCache.Ref<PackIndex> index;

	private volatile DfsBlockCache.Ref<PackReverseIndex> reverseIndex;

	private volatile LongList corruptObjects;

	DfsPackFile(DfsBlockCache cache
		this.cache = cache;
		this.packDesc = desc;
		this.key = key;

		length = desc.getPackSize();
		if (length <= 0)
			length = -1;
	}

	public DfsPackDescription getPackDescription() {
		return packDesc;
	}

	private String getPackName() {
		return packDesc.getPackName();
	}

	void setPackIndex(PackIndex idx) {
		long objCnt = idx.getObjectCount();
		int recSize = Constants.OBJECT_ID_LENGTH + 8;
		int sz = (int) Math.min(objCnt * recSize
		index = cache.put(key
	}

	PackIndex getPackIndex(DfsReader ctx) throws IOException {
		return idx(ctx);
	}

	private PackIndex idx(DfsReader ctx) throws IOException {
		DfsBlockCache.Ref<PackIndex> idxref = index;
		if (idxref != null) {
			PackIndex idx = idxref.get();
			if (idx != null) {
				cache.hit(idxref);
				return idx;
			}
		}

		if (invalid)
			throw new PackInvalidException(getPackName());

		synchronized (initLock) {
			idxref = index;
			if (idxref != null) {
				PackIndex idx = idxref.get();
				if (idx != null) {
					cache.hit(idxref);
					return idx;
				}
			}

			PackIndex idx;
			try {
				ReadableChannel rc = ctx.db.openPackIndex(packDesc);
				try {
					InputStream in = Channels.newInputStream(rc);
					int wantSize = 8192;
					int bs = rc.blockSize();
					if (0 < bs && bs < wantSize)
						bs = (wantSize / bs) * bs;
					else if (bs <= 0)
						bs = wantSize;
					in = new BufferedInputStream(in
					idx = PackIndex.read(in);
				} finally {
					rc.close();
				}
			} catch (IOException e) {
				invalid = true;
				throw e;
			}

			setPackIndex(idx);
			return idx;
		}
	}

	private PackReverseIndex getReverseIdx(DfsReader ctx) throws IOException {
		DfsBlockCache.Ref<PackReverseIndex> revref = reverseIndex;
		if (revref != null) {
			PackReverseIndex revidx = revref.get();
			if (revidx != null) {
				cache.hit(revref);
				return revidx;
			}
		}

		synchronized (initLock) {
			revref = reverseIndex;
			if (revref != null) {
				PackReverseIndex revidx = revref.get();
				if (revidx != null) {
					cache.hit(revref);
					return revidx;
				}
			}

			PackIndex fwdidx = idx(ctx);
			PackReverseIndex revidx = new PackReverseIndex(fwdidx);
			long objCnt = fwdidx.getObjectCount();
			int sz = (int) Math.min(objCnt * 8
			reverseIndex = cache.put(key
			return revidx;
		}
	}

	boolean hasObject(DfsReader ctx
		final long offset = idx(ctx).findOffset(id);
		return 0 < offset && !isCorrupt(offset);
	}

	ObjectLoader get(DfsReader ctx
			throws IOException {
		long offset = idx(ctx).findOffset(id);
		return 0 < offset && !isCorrupt(offset) ? load(ctx
	}

	long findOffset(DfsReader ctx
		return idx(ctx).findOffset(id);
	}

	void resolve(DfsReader ctx
			int matchLimit) throws IOException {
		idx(ctx).resolve(matches
	}

	public void close() {
		cache.remove(this);
		index = null;
		reverseIndex = null;
	}

	long getObjectCount(DfsReader ctx) throws IOException {
		return idx(ctx).getObjectCount();
	}

	ObjectId findObjectForOffset(DfsReader ctx
		return getReverseIdx(ctx).findObject(offset);
	}

	private byte[] decompress(long position
			throws IOException
		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}

		if (ctx.inflate(this
			throw new EOFException(MessageFormat.format(
					JGitText.get().shortCompressedStreamAt
					Long.valueOf(position)));
		return dstbuf;
	}

	void copyPackAsIs(PackOutputStream out
			throws IOException {
		ctx.pin(this
		ctx.copyPackAsIs(this
	}

	void copyAsIs(PackOutputStream out
			boolean validate
			StoredObjectRepresentationNotAvailableException {
		final CRC32 crc1 = validate ? new CRC32() : null;
		final CRC32 crc2 = validate ? new CRC32() : null;
		final byte[] buf = out.getCopyBuffer();

		try {
			readFully(src.offset
		} catch (IOException ioError) {
			StoredObjectRepresentationNotAvailableException gone;
			gone = new StoredObjectRepresentationNotAvailableException(src);
			gone.initCause(ioError);
			throw gone;
		}
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
			if (validate) {
				crc1.update(buf
				crc2.update(buf
			}
		} else if (typeCode == Constants.OBJ_REF_DELTA) {
			if (validate) {
				crc1.update(buf
				crc2.update(buf
			}

			readFully(src.offset + headerCnt
			if (validate) {
				crc1.update(buf
				crc2.update(buf
			}
			headerCnt += 20;
		} else if (validate) {
			crc1.update(buf
			crc2.update(buf
		}

		final long dataOffset = src.offset + headerCnt;
		final long dataLength = src.length;
		final long expectedCRC;
		final DfsBlock quickCopy;

		try {
			quickCopy = ctx.quickCopy(this

			if (validate && idx(ctx).hasCRC32Support()) {
				expectedCRC = idx(ctx).findCRC32(src);
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
				Inflater inf = ctx.inflater();
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
							Long.valueOf(src.offset)));
				}
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
				if (validate)
					crc2.update(buf
				out.write(buf
				pos += n;
				cnt -= n;
			}
			if (validate && crc2.getValue() != expectedCRC) {
				throw new CorruptObjectException(MessageFormat.format(
						JGitText.get().objectAtHasBadZlibStream
						Long.valueOf(src.offset)
			}
		}
	}

	boolean invalid() {
		return invalid;
	}

	void setInvalid() {
		invalid = true;
	}

	private void readFully(long position
			DfsReader ctx) throws IOException {
		if (ctx.copy(this
			throw new EOFException();
	}

	long alignToBlock(long pos) {
		int size = blockSize;
		if (size == 0)
			size = cache.getBlockSize();
		return (pos / size) * size;
	}

	DfsBlock getOrLoadBlock(long pos
		return cache.getOrLoad(this
	}

	DfsBlock readOneBlock(long pos
			throws IOException {
		if (invalid)
			throw new PackInvalidException(getPackName());

		boolean close = true;
		ReadableChannel rc = ctx.db.openPackFile(packDesc);
		try {
			int size = blockSize;
			if (size == 0) {
				size = rc.blockSize();
				if (size <= 0)
					size = cache.getBlockSize();
				else if (size < cache.getBlockSize())
					size = (cache.getBlockSize() / size) * size;
				blockSize = size;
				pos = (pos / size) * size;
			}

			long len = length;
			if (len < 0) {
				len = rc.size();
				if (0 <= len)
					length = len;
			}

			if (0 <= len && len < pos + size)
				size = (int) (len - pos);

			byte[] buf = new byte[size];
			rc.position(pos);
			int cnt = IO.read(rc
			if (cnt != size) {
				if (0 <= len) {
					throw new EOFException(MessageFormat.format(
						    DfsText.get().shortReadOfBlock
						    Long.valueOf(pos)
						    getPackName()
						    Integer.valueOf(size)
						    Integer.valueOf(cnt)));
				}

				byte[] n = new byte[cnt];
				System.arraycopy(buf
				buf = n;
			} else if (len < 0) {
				length = len = rc.size();
			}

			DfsBlock v = new DfsBlock(key
			if (v.end < len)
				close = !cache.readAhead(rc
			return v;
		} finally {
			if (close)
				rc.close();
		}
	}

	ObjectLoader load(DfsReader ctx
			throws IOException {
		try {
			final byte[] ib = ctx.tempId;
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
					sz += (c & 0x7f) << shift;
					shift += 7;
				}

				switch (typeCode) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG: {
					if (delta != null) {
						data = decompress(pos + p
						type = typeCode;
						break SEARCH;
					}

					if (sz < ctx.getStreamFileThreshold()) {
						data = decompress(pos + p
						if (data != null)
							return new ObjectLoader.SmallObject(typeCode
					}
					return new LargePackedWholeObject(typeCode
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

					DeltaBaseCache.Entry e = ctx.getDeltaBaseCache().get(key
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
					long base = findDeltaBase(ctx
					delta = new Delta(delta
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = ctx.getDeltaBaseCache().get(key
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
				}
			}


			if (data == null)
				throw new LargeObjectException();

			do {
				if (cached)
					cached = false;
				else if (delta.next == null)
					ctx.getDeltaBaseCache().put(key

				pos = delta.deltaPos;

				byte[] cmds = decompress(pos + delta.hdrLen
				if (cmds == null) {
					throw new LargeObjectException();
				}

				final long sz = BinaryDelta.getResultSize(cmds);
				if (Integer.MAX_VALUE <= sz)
					throw new LargeObjectException.ExceedsByteArrayLimit();

				final byte[] result;
				try {
					result = new byte[(int) sz];
				} catch (OutOfMemoryError tooBig) {
					cmds = null;
					throw new LargeObjectException.OutOfMemory(tooBig);
				}

				BinaryDelta.apply(data
				data = result;
				delta = delta.next;
			} while (delta != null);

			return new ObjectLoader.SmallObject(type

		} catch (DataFormatException dfe) {
			CorruptObjectException coe = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getPackName()));
			coe.initCause(dfe);
			throw coe;
		}
	}

	private long findDeltaBase(DfsReader ctx
			throws IOException
		long ofs = idx(ctx).findOffset(baseId);
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

	byte[] getDeltaHeader(DfsReader wc
			throws IOException
		final byte[] hdr = new byte[32];
		wc.inflate(this
		return hdr;
	}

	int getObjectType(DfsReader ctx
		final byte[] ib = ctx.tempId;
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
				pos = findDeltaBase(ctx
				continue;
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		}
	}

	long getObjectSize(DfsReader ctx
		final long offset = idx(ctx).findOffset(id);
		return 0 < offset ? getObjectSize(ctx
	}

	long getObjectSize(DfsReader ctx
			throws IOException {
		final byte[] ib = ctx.tempId;
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
			return BinaryDelta.getResultSize(getDeltaHeader(ctx
		} catch (DataFormatException dfe) {
			CorruptObjectException coe = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getPackName()));
			coe.initCause(dfe);
			throw coe;
		}
	}

	void representation(DfsReader ctx
			throws IOException {
		final long pos = r.offset;
		final byte[] ib = ctx.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		int p = 1;
		final int typeCode = (c >> 4) & 7;
		while ((c & 0x80) != 0)
			c = ib[p++] & 0xff;

		long len = (getReverseIdx(ctx).findNextOffset(pos
		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			r.format = StoredObjectRepresentation.PACK_WHOLE;
			r.length = len - p;
			return;

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
			r.format = StoredObjectRepresentation.PACK_DELTA;
			r.baseId = findObjectForOffset(ctx
			r.length = len - p;
			return;
		}

		case Constants.OBJ_REF_DELTA: {
			len -= p;
			len -= Constants.OBJECT_ID_LENGTH;
			readFully(pos + p
			ObjectId id = ObjectId.fromRaw(ib);
			r.format = StoredObjectRepresentation.PACK_DELTA;
			r.baseId = id;
			r.length = len;
			return;
		}

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
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
			synchronized (initLock) {
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
