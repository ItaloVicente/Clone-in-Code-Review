
package org.eclipse.jgit.storage.dfs;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.file.PackIndexWriter;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsInserter extends ObjectInserter {
	private static final int INDEX_VERSION = 2;

	private final DfsObjDatabase db;

	private List<PackedObjectInfo> objectList;
	private ObjectIdOwnerMap<PackedObjectInfo> objectMap;

	private DfsBlockCache cache;
	private DfsPackKey packKey;
	private DfsPackDescription packDsc;
	private PackStream packOut;
	private boolean rollback;

	protected DfsInserter(DfsObjDatabase db) {
		this.db = db;
	}

	@Override
	public DfsPackParser newPackParser(InputStream in) throws IOException {
		return new DfsPackParser(db
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		ObjectId id = idFor(type
		if (objectMap != null && objectMap.contains(id))
			return id;
		if (db.has(id))
			return id;

		long offset = beginObject(type
		packOut.compress.write(data
		packOut.compress.finish();
		return endObject(id
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		byte[] buf = buffer();
		if (len <= buf.length) {
			IO.readFully(in
			return insert(type
		}

		long offset = beginObject(type
		MessageDigest md = digest();
		while (0 < len) {
			int n = in.read(buf
			if (n <= 0)
				throw new EOFException();
			md.update(buf
			packOut.compress.write(buf
			len -= n;
		}
		packOut.compress.finish();
		return endObject(ObjectId.fromRaw(md.digest())
	}

	@Override
	public void flush() throws IOException {
		if (packDsc == null)
			return;

		if (packOut == null)
			throw new IOException();

		byte[] packHash = packOut.writePackFooter();
		packDsc.setPackSize(packOut.getCount());
		packOut.close();
		packOut = null;

		sortObjectsById();

		PackIndex index = writePackIndex(packDsc
		db.commitPack(Collections.singletonList(packDsc)
		rollback = false;

		DfsPackFile p = cache.getOrCreate(packDsc
		if (index != null)
			p.setPackIndex(index);
		db.addPack(p);
		clear();
	}

	@Override
	public void release() {
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
		objectList = new BlockList<PackedObjectInfo>();
		objectMap = new ObjectIdOwnerMap<PackedObjectInfo>();
		cache = DfsBlockCache.getInstance();

		rollback = true;
		packDsc = db.newPack(DfsObjDatabase.PackSource.INSERT);
		packOut = new PackStream(db.writePackFile(packDsc));
		packKey = new DfsPackKey();

		byte[] buf = packOut.hdrBuf;
		System.arraycopy(Constants.PACK_SIGNATURE
		NB.encodeInt32(buf
		NB.encodeInt32(buf
		packOut.write(buf
	}

	@SuppressWarnings("unchecked")
	private void sortObjectsById() {
		Collections.sort(objectList);
	}

	PackIndex writePackIndex(DfsPackDescription pack
			List<PackedObjectInfo> list) throws IOException {
		pack.setObjectCount(list.size());

		TemporaryBuffer.Heap buf = null;
		PackIndex packIndex = null;
		if (list.size() <= 58000) {
			buf = new TemporaryBuffer.Heap(2 << 20);
			index(buf
			packIndex = PackIndex.read(buf.openInputStream());
		}

		DfsOutputStream os = db.writePackIndex(pack);
		try {
			CountingOutputStream cnt = new CountingOutputStream(os);
			if (buf != null)
				buf.writeTo(cnt
			else
				index(cnt
			pack.setIndexSize(cnt.getCount());
		} finally {
			os.close();
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
		private final byte[] hdrBuf;
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
			deflater = new Deflater(Deflater.BEST_COMPRESSION);
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
		public void write(final int b) throws IOException {
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

		@Override
		public void close() throws IOException {
			deflater.end();
			out.close();
		}
	}
}
