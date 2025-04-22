
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.eclipse.jgit.lib.Constants.OBJ_OFS_DELTA;
import static org.eclipse.jgit.lib.Constants.OBJ_REF_DELTA;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackIndexWriter;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.CountingOutputStream;
import org.eclipse.jgit.util.sha1.SHA1;

public class DfsInserter extends ObjectInserter {
	private static final int INDEX_VERSION = 2;

	final DfsObjDatabase db;
	int compression = Deflater.BEST_COMPRESSION;

	List<PackedObjectInfo> objectList;
	ObjectIdOwnerMap<PackedObjectInfo> objectMap;

	DfsBlockCache cache;
	DfsStreamKey packKey;
	DfsPackDescription packDsc;
	PackStream packOut;
	private boolean rollback;
	private boolean checkExisting = true;

	protected DfsInserter(DfsObjDatabase db) {
		this.db = db;
	}

	public void checkExisting(boolean check) {
		checkExisting = check;
	}

	void setCompressionLevel(int compression) {
		this.compression = compression;
	}

	@Override
	public DfsPackParser newPackParser(InputStream in) throws IOException {
		return new DfsPackParser(db
	}

	@Override
	public ObjectReader newReader() {
		return new Reader();
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		ObjectId id = idFor(type
		if (objectMap != null && objectMap.contains(id))
			return id;
		if (checkExisting && db.has(id
			return id;

		long offset = beginObject(type
		packOut.compress.write(data
		packOut.compress.finish();
		return endObject(id
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		byte[] buf = insertBuffer(len);
		if (len <= buf.length) {
			IO.readFully(in
			return insert(type
		}

		long offset = beginObject(type
		SHA1 md = digest();
		md.update(Constants.encodedTypeString(type));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(len));
		md.update((byte) 0);

		while (0 < len) {
			int n = in.read(buf
			if (n <= 0)
				throw new EOFException();
			md.update(buf
			packOut.compress.write(buf
			len -= n;
		}
		packOut.compress.finish();
		return endObject(md.toObjectId()
	}

	private byte[] insertBuffer(long len) {
		byte[] buf = buffer();
		if (len <= buf.length)
			return buf;
		if (len < db.getReaderOptions().getStreamFileThreshold()) {
			try {
				return new byte[(int) len];
			} catch (OutOfMemoryError noMem) {
				return buf;
			}
		}
		return buf;
	}

	@Override
	public void flush() throws IOException {
		if (packDsc == null)
			return;

		if (packOut == null)
			throw new IOException();

		byte[] packHash = packOut.writePackFooter();
		packDsc.addFileExt(PACK);
		packDsc.setFileSize(PACK
		packOut.close();
		packOut = null;

		sortObjectsById();

		PackIndex index = writePackIndex(packDsc
		db.commitPack(Collections.singletonList(packDsc)
		rollback = false;

		DfsPackFile p = new DfsPackFile(cache
		if (index != null)
			p.setPackIndex(index);
		db.addPack(p);
		clear();
	}

	@Override
	public void close() {
		if (packOut != null) {
			try {
				packOut.close();
			} catch (IOException err) {
			} finally {
				packOut = null;
			}
		}
		if (rollback && packDsc != null) {
			try {
				db.rollbackPack(Collections.singletonList(packDsc));
			} finally {
				packDsc = null;
				rollback = false;
			}
		}
		clear();
	}

	private void clear() {
		objectList = null;
		objectMap = null;
		packKey = null;
		packDsc = null;
	}

	private long beginObject(int type
		if (packOut == null)
			beginPack();
		long offset = packOut.getCount();
		packOut.beginObject(type
		return offset;
	}

	private ObjectId endObject(ObjectId id
		PackedObjectInfo obj = new PackedObjectInfo(id);
		obj.setOffset(offset);
		obj.setCRC((int) packOut.crc32.getValue());
		objectList.add(obj);
		objectMap.addIfAbsent(obj);
		return id;
	}

	private void beginPack() throws IOException {
		objectList = new BlockList<>();
		objectMap = new ObjectIdOwnerMap<>();
		cache = DfsBlockCache.getInstance();

		rollback = true;
		packDsc = db.newPack(DfsObjDatabase.PackSource.INSERT);
		DfsOutputStream dfsOut = db.writeFile(packDsc
		packDsc.setBlockSize(PACK
		packOut = new PackStream(dfsOut);
		packKey = packDsc.getStreamKey(PACK);

		byte[] buf = packOut.hdrBuf;
		System.arraycopy(Constants.PACK_SIGNATURE
		NB.encodeInt32(buf
		NB.encodeInt32(buf
		packOut.write(buf
	}

	private void sortObjectsById() {
		Collections.sort(objectList);
	}

	@Nullable
	private TemporaryBuffer.Heap maybeGetTemporaryBuffer(
			List<PackedObjectInfo> list) {
		if (list.size() <= 58000) {
			return new TemporaryBuffer.Heap(2 << 20);
		}
		return null;
	}

	PackIndex writePackIndex(DfsPackDescription pack
			List<PackedObjectInfo> list) throws IOException {
		pack.setIndexVersion(INDEX_VERSION);
		pack.setObjectCount(list.size());

		PackIndex packIndex = null;
		try (TemporaryBuffer.Heap buf = maybeGetTemporaryBuffer(list);
				DfsOutputStream os = db.writeFile(pack
				CountingOutputStream cnt = new CountingOutputStream(os)) {
			if (buf != null) {
				index(buf
				packIndex = PackIndex.read(buf.openInputStream());
				buf.writeTo(cnt
			} else {
				index(cnt
			}
			pack.addFileExt(INDEX);
			pack.setBlockSize(INDEX
			pack.setFileSize(INDEX
		}
		return packIndex;
	}

	private static void index(OutputStream out
			List<PackedObjectInfo> list) throws IOException {
		PackIndexWriter.createVersion(out
	}

	private class PackStream extends OutputStream {
		private final DfsOutputStream out;
		private final MessageDigest md;
		final byte[] hdrBuf;
		private final Deflater deflater;
		private final int blockSize;

		private byte[] currBuf;

		final CRC32 crc32;
		final DeflaterOutputStream compress;

		PackStream(DfsOutputStream out) {
			this.out = out;

			hdrBuf = new byte[32];
			md = Constants.newMessageDigest();
			crc32 = new CRC32();
			deflater = new Deflater(compression);
			compress = new DeflaterOutputStream(this

			int size = out.blockSize();
			if (size <= 0)
				size = cache.getBlockSize();
			else if (size < cache.getBlockSize())
				size = (cache.getBlockSize() / size) * size;
			blockSize = size;
			currBuf = new byte[blockSize];
		}

		long getCount() {
			return currPos + currPtr;
		}

		void beginObject(int objectType
			crc32.reset();
			deflater.reset();
			write(hdrBuf
		}

		private int encodeTypeSize(int type
			long nextLength = rawLength >>> 4;
			hdrBuf[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (rawLength & 0x0F));
			rawLength = nextLength;
			int n = 1;
			while (rawLength > 0) {
				nextLength >>>= 7;
				hdrBuf[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
				rawLength = nextLength;
			}
			return n;
		}

		@Override
		public void write(int b) throws IOException {
			hdrBuf[0] = (byte) b;
			write(hdrBuf
		}

		@Override
		public void write(byte[] data
			crc32.update(data
			md.update(data
			writeNoHash(data
		}

		private void writeNoHash(byte[] data
				throws IOException {
			while (0 < len) {
				int n = Math.min(len
				if (n == 0) {
					flushBlock();
					currBuf = new byte[blockSize];
					continue;
				}

				System.arraycopy(data
				off += n;
				len -= n;
				currPtr += n;
			}
		}

		private void flushBlock() throws IOException {
			out.write(currBuf

			byte[] buf;
			if (currPtr == currBuf.length)
				buf = currBuf;
			else
				buf = copyOf(currBuf
			cache.put(new DfsBlock(packKey

			currPos += currPtr;
			currPtr = 0;
			currBuf = null;
		}

		private byte[] copyOf(byte[] src
			byte[] dst = new byte[cnt];
			System.arraycopy(src
			return dst;
		}

		byte[] writePackFooter() throws IOException {
			byte[] packHash = md.digest();
			writeNoHash(packHash
			if (currPtr != 0)
				flushBlock();
			return packHash;
		}

		int read(long pos
			int r = 0;
			while (pos < currPos && r < cnt) {
				DfsBlock b = getOrLoadBlock(pos);
				int n = b.copy(pos
				pos += n;
				r += n;
			}
			if (currPos <= pos && r < cnt) {
				int s = (int) (pos - currPos);
				int n = Math.min(currPtr - s
				System.arraycopy(currBuf
				r += n;
			}
			return r;
		}

		byte[] inflate(DfsReader ctx
				DataFormatException {
			byte[] dstbuf;
			try {
				dstbuf = new byte[len];
			} catch (OutOfMemoryError noMemory) {
			}

			Inflater inf = ctx.inflater();
			pos += setInput(pos
			for (int dstoff = 0;;) {
				int n = inf.inflate(dstbuf
				dstoff += n;
				if (inf.finished())
					return dstbuf;
				if (inf.needsInput())
					pos += setInput(pos
				else if (n == 0)
					throw new DataFormatException();
			}
		}

		private int setInput(long pos
				throws IOException
			if (pos < currPos)
				return getOrLoadBlock(pos).setInput(pos
			if (pos < currPos + currPtr) {
				int s = (int) (pos - currPos);
				int n = currPtr - s;
				inf.setInput(currBuf
				return n;
			}
			throw new EOFException(JGitText.get().unexpectedEofInPack);
		}

		private DfsBlock getOrLoadBlock(long pos) throws IOException {
			long s = toBlockStart(pos);
			DfsBlock b = cache.get(packKey
			if (b != null)
				return b;

			byte[] d = new byte[blockSize];
			for (int p = 0; p < blockSize;) {
				int n = out.read(s + p
				if (n <= 0)
					throw new EOFException(JGitText.get().unexpectedEofInPack);
				p += n;
			}
			b = new DfsBlock(packKey
			cache.put(b);
			return b;
		}

		private long toBlockStart(long pos) {
			return (pos / blockSize) * blockSize;
		}

		@Override
		public void close() throws IOException {
			deflater.end();
			out.close();
		}
	}

	private class Reader extends ObjectReader {
		private final DfsReader ctx = db.newReader();

		@Override
		public ObjectReader newReader() {
			return db.newReader();
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			Collection<ObjectId> stored = ctx.resolve(id);
			if (objectList == null)
				return stored;

			Set<ObjectId> r = new HashSet<>(stored.size() + 2);
			r.addAll(stored);
			for (PackedObjectInfo obj : objectList) {
				if (id.prefixCompare(obj) == 0)
					r.add(obj.copy());
			}
			return r;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws IOException {
			if (objectMap == null)
				return ctx.open(objectId

			PackedObjectInfo obj = objectMap.get(objectId);
			if (obj == null)
				return ctx.open(objectId

			byte[] buf = buffer();
			int cnt = packOut.read(obj.getOffset()
			if (cnt <= 0)
					throw new EOFException(JGitText.get().unexpectedEofInPack);

			int c = buf[0] & 0xff;
			int type = (c >> 4) & 7;
			if (type == OBJ_OFS_DELTA || type == OBJ_REF_DELTA)
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotReadBackDelta
			if (typeHint != OBJ_ANY && type != typeHint) {
				throw new IncorrectObjectTypeException(objectId.copy()
			}

			long sz = c & 0x0f;
			int ptr = 1;
			int shift = 4;
			while ((c & 0x80) != 0) {
				if (ptr >= cnt)
					throw new EOFException(JGitText.get().unexpectedEofInPack);
				c = buf[ptr++] & 0xff;
				sz += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}

			long zpos = obj.getOffset() + ptr;
			if (sz < ctx.getStreamFileThreshold()) {
				byte[] data = inflate(obj
				if (data != null)
					return new ObjectLoader.SmallObject(type
			}
			return new StreamLoader(obj.copy()
		}

		private byte[] inflate(PackedObjectInfo obj
				throws IOException
			try {
				return packOut.inflate(ctx
			} catch (DataFormatException dfe) {
				throw new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().objectAtHasBadZlibStream
								Long.valueOf(obj.getOffset())
								packDsc.getFileName(PackExt.PACK))
						dfe);
			}
		}

		@Override
		public boolean has(AnyObjectId objectId) throws IOException {
			return (objectMap != null && objectMap.contains(objectId))
					|| ctx.has(objectId);
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return ctx.getShallowCommits();
		}

		@Override
		public ObjectInserter getCreatedFromInserter() {
			return DfsInserter.this;
		}

		@Override
		public void close() {
			ctx.close();
		}
	}

	private class StreamLoader extends ObjectLoader {
		private final ObjectId id;
		private final int type;
		private final long size;

		private final DfsStreamKey srcPack;
		private final long pos;

		StreamLoader(ObjectId id
				DfsStreamKey key
			this.id = id;
			this.type = type;
			this.size = sz;
			this.srcPack = key;
			this.pos = pos;
		}

		@Override
		public ObjectStream openStream() throws IOException {
			final DfsReader ctx = db.newReader();
			if (srcPack != packKey) {
				try {
					return ctx.open(id
				} finally {
					ctx.close();
				}
			}

			int bufsz = 8192;
			final Inflater inf = ctx.inflater();
			return new ObjectStream.Filter(type
					size
							new ReadBackStream(pos)
				@Override
				public void close() throws IOException {
					ctx.close();
					super.close();
				}
			};
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException.ExceedsLimit(
					db.getReaderOptions().getStreamFileThreshold()
		}
	}

	private final class ReadBackStream extends InputStream {
		private long pos;

		ReadBackStream(long offset) {
			pos = offset;
		}

		@Override
		public int read() throws IOException {
			byte[] b = new byte[1];
			int n = read(b);
			return n == 1 ? b[0] & 0xff : -1;
		}

		@Override
		public int read(byte[] buf
			int n = packOut.read(pos
			if (n > 0) {
				pos += n;
			}
			return n;
		}
	}
}
