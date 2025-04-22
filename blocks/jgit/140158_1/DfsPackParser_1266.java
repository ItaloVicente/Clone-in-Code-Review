
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;
import static org.eclipse.jgit.internal.storage.pack.PackExt.BITMAP_INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndex;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackReverseIndex;
import org.eclipse.jgit.internal.storage.pack.BinaryDelta;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.LongList;

public final class DfsPackFile extends BlockBasedFile {
	private static final int REC_SIZE = Constants.OBJECT_ID_LENGTH + 8;

	private final Object initLock = new Object();

	private volatile PackIndex index;

	private volatile PackReverseIndex reverseIndex;

	private volatile PackBitmapIndex bitmapIndex;

	private volatile LongList corruptObjects;

	DfsPackFile(DfsBlockCache cache
		super(cache

		int bs = desc.getBlockSize(PACK);
		if (bs > 0) {
			setBlockSize(bs);
		}

		long sz = desc.getFileSize(PACK);
		length = sz > 0 ? sz : -1;
	}

	public DfsPackDescription getPackDescription() {
		return desc;
	}

	public boolean isIndexLoaded() {
		return index != null;
	}

	void setPackIndex(PackIndex idx) {
		long objCnt = idx.getObjectCount();
		int recSize = Constants.OBJECT_ID_LENGTH + 8;
		long sz = objCnt * recSize;
		cache.putRef(desc.getStreamKey(INDEX)
		index = idx;
	}

	public PackIndex getPackIndex(DfsReader ctx) throws IOException {
		return idx(ctx);
	}

	private PackIndex idx(DfsReader ctx) throws IOException {
		if (index != null) {
			return index;
		}

		if (invalid) {
			throw new PackInvalidException(getFileName());
		}

		Repository.getGlobalListenerList()
				.dispatch(new BeforeDfsPackIndexLoadedEvent(this));

		synchronized (initLock) {
			if (index != null) {
				return index;
			}

			try {
				DfsStreamKey idxKey = desc.getStreamKey(INDEX);
				DfsBlockCache.Ref<PackIndex> idxref = cache.getOrLoadRef(idxKey
						() -> {
							try {
								ctx.stats.readIdx++;
								long start = System.nanoTime();
								try (ReadableChannel rc = ctx.db.openFile(desc
										INDEX)) {
									InputStream in = Channels
											.newInputStream(rc);
									int wantSize = 8192;
									int bs = rc.blockSize();
									if (0 < bs && bs < wantSize) {
										bs = (wantSize / bs) * bs;
									} else if (bs <= 0) {
										bs = wantSize;
									}
									PackIndex idx = PackIndex.read(
											new BufferedInputStream(in
									int sz = (int) Math.min(
											idx.getObjectCount() * REC_SIZE
											Integer.MAX_VALUE);
									ctx.stats.readIdxBytes += rc.position();
									index = idx;
									return new DfsBlockCache.Ref<>(idxKey
											sz
								} finally {
									ctx.stats.readIdxMicros += elapsedMicros(
											start);
								}
							} catch (EOFException e) {
								throw new IOException(MessageFormat.format(
										DfsText.get().shortReadOfIndex
										desc.getFileName(INDEX))
							} catch (IOException e) {
								throw new IOException(MessageFormat.format(
										DfsText.get().cannotReadIndex
										desc.getFileName(INDEX))
							}
						});
				PackIndex idx = idxref.get();
				if (index == null && idx != null) {
					index = idx;
				}
				return index;
			} catch (IOException e) {
				invalid = true;
				throw e;
			}
		}
	}

	final boolean isGarbage() {
		return desc.getPackSource() == UNREACHABLE_GARBAGE;
	}

	PackBitmapIndex getBitmapIndex(DfsReader ctx) throws IOException {
		if (invalid || isGarbage() || !desc.hasFileExt(BITMAP_INDEX)) {
			return null;
		}

		if (bitmapIndex != null) {
			return bitmapIndex;
		}

		synchronized (initLock) {
			if (bitmapIndex != null) {
				return bitmapIndex;
			}

			PackIndex idx = idx(ctx);
			PackReverseIndex revidx = getReverseIdx(ctx);
			DfsStreamKey bitmapKey = desc.getStreamKey(BITMAP_INDEX);
			DfsBlockCache.Ref<PackBitmapIndex> idxref = cache
					.getOrLoadRef(bitmapKey
						ctx.stats.readBitmap++;
						long start = System.nanoTime();
						try (ReadableChannel rc = ctx.db.openFile(desc
								BITMAP_INDEX)) {
							long size;
							PackBitmapIndex bmidx;
							try {
								InputStream in = Channels.newInputStream(rc);
								int wantSize = 8192;
								int bs = rc.blockSize();
								if (0 < bs && bs < wantSize) {
									bs = (wantSize / bs) * bs;
								} else if (bs <= 0) {
									bs = wantSize;
								}
								in = new BufferedInputStream(in
								bmidx = PackBitmapIndex.read(in
							} finally {
								size = rc.position();
								ctx.stats.readIdxBytes += size;
								ctx.stats.readIdxMicros += elapsedMicros(start);
							}
							int sz = (int) Math.min(size
							bitmapIndex = bmidx;
							return new DfsBlockCache.Ref<>(bitmapKey
									bmidx);
						} catch (EOFException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().shortReadOfIndex
									desc.getFileName(BITMAP_INDEX))
						} catch (IOException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().cannotReadIndex
									desc.getFileName(BITMAP_INDEX))
						}
					});
			PackBitmapIndex bmidx = idxref.get();
			if (bitmapIndex == null && bmidx != null) {
				bitmapIndex = bmidx;
			}
			return bitmapIndex;
		}
	}

	PackReverseIndex getReverseIdx(DfsReader ctx) throws IOException {
		if (reverseIndex != null) {
			return reverseIndex;
		}

		synchronized (initLock) {
			if (reverseIndex != null) {
				return reverseIndex;
			}

			PackIndex idx = idx(ctx);
			DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
					desc.getStreamKey(INDEX));
			DfsBlockCache.Ref<PackReverseIndex> revref = cache
					.getOrLoadRef(revKey
						PackReverseIndex revidx = new PackReverseIndex(idx);
						int sz = (int) Math.min(idx.getObjectCount() * 8
								Integer.MAX_VALUE);
						reverseIndex = revidx;
						return new DfsBlockCache.Ref<>(revKey
					});
			PackReverseIndex revidx = revref.get();
			if (reverseIndex == null && revidx != null) {
				reverseIndex = revidx;
			}
			return reverseIndex;
		}
	}

	public boolean hasObject(DfsReader ctx
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

	long getObjectCount(DfsReader ctx) throws IOException {
		return idx(ctx).getObjectCount();
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
		}
		return dstbuf;
	}

	void copyPackAsIs(PackOutputStream out
		if (length == -1) {
			ctx.pin(this
			ctx.unpin();
		}
		try (ReadableChannel rc = ctx.db.openFile(desc
			int sz = ctx.getOptions().getStreamPackBufferSize();
			if (sz > 0) {
				rc.setReadAheadBytes(sz);
			}
			if (cache.shouldCopyThroughCache(length)) {
				copyPackThroughCache(out
			} else {
				copyPackBypassCache(out
			}
		}
	}

	private void copyPackThroughCache(PackOutputStream out
			ReadableChannel rc) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this
			int ptr = (int) (position - b.start);
			if (b.size() <= ptr) {
				throw packfileIsTruncated();
			}
			int n = (int) Math.min(b.size() - ptr
			b.write(out
			position += n;
			remaining -= n;
		}
	}

	private long copyPackBypassCache(PackOutputStream out
			throws IOException {
		ByteBuffer buf = newCopyBuffer(out
		long position = 12;
		long remaining = length - (12 + 20);
		boolean packHeadSkipped = false;
		while (0 < remaining) {
			DfsBlock b = cache.get(key
			if (b != null) {
				int ptr = (int) (position - b.start);
				if (b.size() <= ptr) {
					throw packfileIsTruncated();
				}
				int n = (int) Math.min(b.size() - ptr
				b.write(out
				position += n;
				remaining -= n;
				rc.position(position);
				packHeadSkipped = true;
				continue;
			}

			int ptr = packHeadSkipped ? 0 : 12;
			buf.position(0);
			int bufLen = read(rc
			if (bufLen <= ptr) {
				throw packfileIsTruncated();
			}
			int n = (int) Math.min(bufLen - ptr
			out.write(buf.array()
			position += n;
			remaining -= n;
			packHeadSkipped = true;
		}
		return position;
	}

	private ByteBuffer newCopyBuffer(PackOutputStream out
		int bs = blockSize(rc);
		byte[] copyBuf = out.getCopyBuffer();
		if (bs > copyBuf.length) {
			copyBuf = new byte[bs];
		}
		return ByteBuffer.wrap(copyBuf
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
			throw new StoredObjectRepresentationNotAvailableException(src
					ioError);
		}
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
		final DfsBlock quickCopy;

		try {
			quickCopy = ctx.quickCopy(this

			if (validate && idx(ctx).hasCRC32Support()) {
				assert(crc1 != null);
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
				assert(crc1 != null);
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
						}
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

	private IOException packfileIsTruncated() {
		invalid = true;
		return new IOException(MessageFormat.format(
				JGitText.get().packfileIsTruncated
	}

	private void readFully(long position
			DfsReader ctx) throws IOException {
		if (ctx.copy(this
			throw new EOFException();
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
					sz += ((long) (c & 0x7f)) << shift;
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
						if (data != null) {
							return new ObjectLoader.SmallObject(typeCode
						}
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
					if (sz != delta.deltaSize) {
						break SEARCH;
					}

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
					if (sz != delta.deltaSize) {
						break SEARCH;
					}

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

			assert(delta != null);
			do {
				if (cached) {
					cached = false;
				} else if (delta.next == null) {
					ctx.getDeltaBaseCache().put(key
				}

				pos = delta.deltaPos;

				byte[] cmds = decompress(pos + delta.hdrLen
				if (cmds == null) {
					throw new LargeObjectException();
				}

				final long sz = BinaryDelta.getResultSize(cmds);
				if (Integer.MAX_VALUE <= sz) {
					throw new LargeObjectException.ExceedsByteArrayLimit();
				}

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
			throw new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getFileName())
					dfe);
		}
	}

	private long findDeltaBase(DfsReader ctx
			throws IOException
		long ofs = idx(ctx).findOffset(baseId);
		if (ofs < 0) {
			throw new MissingObjectException(baseId
					JGitText.get().missingDeltaBase);
		}
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
				while ((c & 0x80) != 0) {
					c = ib[p++] & 0xff;
				}
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
				while ((c & 0x80) != 0) {
					c = ib[p++] & 0xff;
				}
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
			while ((c & 128) != 0) {
				c = ib[p++] & 0xff;
			}
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
			throw new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							getFileName())
					dfe);
		}
	}

	void representation(DfsObjectRepresentation r
			DfsReader ctx
			throws IOException {
		r.offset = pos;
		final byte[] ib = ctx.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		int p = 1;
		final int typeCode = (c >> 4) & 7;
		while ((c & 0x80) != 0) {
			c = ib[p++] & 0xff;
		}

		long len = rev.findNextOffset(pos
		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			r.format = StoredObjectRepresentation.PACK_WHOLE;
			r.baseId = null;
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
			r.format = StoredObjectRepresentation.PACK_DELTA;
			r.baseId = rev.findObject(pos - ofs);
			r.length = len - p;
			return;
		}

		case Constants.OBJ_REF_DELTA: {
			readFully(pos + p
			r.format = StoredObjectRepresentation.PACK_DELTA;
			r.baseId = ObjectId.fromRaw(ib);
			r.length = len - p - 20;
			return;
		}

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
	}

	boolean isCorrupt(long offset) {
		LongList list = corruptObjects;
		if (list == null) {
			return false;
		}
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
