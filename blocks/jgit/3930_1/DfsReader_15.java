
package org.eclipse.jgit.storage.dfs;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.file.PackIndex;
import org.eclipse.jgit.storage.file.PackIndexWriter;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.CountingOutputStream;

final class DfsPackParser extends PackParser {
	private static final int INDEX_VERSION = 2;

	private final DfsObjDatabase objdb;

	private final CRC32 crc;

	private final MessageDigest packDigest;

	private int blockSize;

	private long packEnd;

	private byte[] packHash;

	private Deflater def;

	private DfsPackDescription packDsc;

	private DfsPackKey packKey;

	private PackIndex packIndex;

	private DfsOutputStream out;

	private byte[] currBuf;

	private DfsBlockCache blockCache;

	private long readPos;
	private DfsBlock readBlock;

	DfsPackParser(DfsObjDatabase objdb
		super(objdb
		this.objdb = objdb;
		this.crc = new CRC32();
		this.packDigest = Constants.newMessageDigest();
	}

	@Override
	public PackLock parse(ProgressMonitor receiving
			throws IOException {
		boolean rollback = true;
		try {
			blockCache = DfsBlockCache.getInstance();
			super.parse(receiving
			buffer(packHash
			if (currEnd != 0)
				flushBlock();
			out.close();
			out = null;
			currBuf = null;
			readBlock = null;
			packDsc.setPackSize(packEnd);

			writePackIndex();
			objdb.commitPack(packDsc);

			DfsPackFile p = blockCache.getOrCreate(packDsc
			if (packIndex != null)
				p.setPackIndex(packIndex);

			rollback = false;
			objdb.addPack(p);

			return null;
		} finally {
			blockCache = null;
			currBuf = null;
			readBlock = null;

			if (def != null) {
				def.end();
				def = null;
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException err) {
				}
				out = null;
			}

			if (rollback && packDsc != null)
				objdb.rollbackPack(packDsc);
		}
	}

	@Override
	protected void onPackHeader(long objectCount) throws IOException {
		packDsc = objdb.newPack(objectCount);
		packKey = new DfsPackKey();

		out = objdb.writePackFile(packDsc);
		int size = out.blockSize();
		if (size <= 0)
			size = blockCache.getBlockSize();
		else if (size < blockCache.getBlockSize())
			size = (blockCache.getBlockSize() / size) * size;
		blockSize = size;
		packDsc.setPackBlockSize(blockSize);
		currBuf = new byte[blockSize];
	}

	@Override
	protected void onBeginWholeObject(long streamPosition
			long inflatedSize) throws IOException {
		crc.reset();
	}

	@Override
	protected void onEndWholeObject(PackedObjectInfo info) throws IOException {
		info.setCRC((int) crc.getValue());
	}

	@Override
	protected void onBeginOfsDelta(long streamPosition
			long baseStreamPosition
		crc.reset();
	}

	@Override
	protected void onBeginRefDelta(long streamPosition
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
	protected void onStoreStream(byte[] raw
			throws IOException {
		buffer(raw
		packDigest.update(raw
	}

	private void buffer(byte[] raw
		while (0 < len) {
			int n = Math.min(len
			if (n == 0) {
				DfsBlock v = flushBlock();
				currBuf = new byte[blockSize];
				currEnd = 0;
				currPos += v.size();
				continue;
			}

			System.arraycopy(raw
			pos += n;
			len -= n;
			currEnd += n;
			packEnd += n;
		}
	}

	private DfsBlock flushBlock() throws IOException {
		out.write(currBuf

		byte[] buf;
		if (currEnd == currBuf.length) {
			buf = currBuf;
		} else {
			buf = new byte[currEnd];
			System.arraycopy(currBuf
		}

		DfsBlock v = new DfsBlock(packKey
		readBlock = v;
		blockCache.put(v);
		return v;
	}

	@Override
	protected void onPackFooter(byte[] hash) throws IOException {
		packHash = hash;
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(PackedObjectInfo obj
			ObjectTypeAndSize info) throws IOException {
		readPos = obj.getOffset();
		crc.reset();
		return readObjectHeader(info);
	}

	@Override
	protected ObjectTypeAndSize seekDatabase(UnresolvedDelta delta
			ObjectTypeAndSize info) throws IOException {
		readPos = delta.getOffset();
		crc.reset();
		return readObjectHeader(info);
	}

	@Override
	protected int readDatabase(byte[] dst
		if (cnt == 0)
			return 0;

		if (currPos <= readPos) {
			int p = (int) (readPos - currPos);
			int n = Math.min(cnt
			if (n == 0)
				throw new EOFException();
			System.arraycopy(currBuf
			readPos += n;
			return n;
		}

		if (readBlock == null || !readBlock.contains(packKey
			long start = toBlockStart(readPos);
			readBlock = blockCache.get(packKey
			if (readBlock == null) {
				int size = (int) Math.min(blockSize
				byte[] buf = new byte[size];
				if (read(start
					throw new EOFException();
				readBlock = new DfsBlock(packKey
				blockCache.put(readBlock);
			}
		}

		int n = readBlock.copy(readPos
		readPos += n;
		return n;
	}

	private int read(long pos
		if (len == 0)
			return 0;

		int cnt = 0;
		while (0 < len) {
			int r = out.read(pos
			if (r <= 0)
				break;
			pos += r;
			off += r;
			len -= r;
			cnt += r;
		}
		return cnt != 0 ? cnt : -1;
	}

	private long toBlockStart(long pos) {
		return (pos / blockSize) * blockSize;
	}

	@Override
	protected boolean checkCRC(int oldCRC) {
		return oldCRC == (int) crc.getValue();
	}

	@Override
	protected boolean onAppendBase(final int typeCode
			final PackedObjectInfo info) throws IOException {
		info.setOffset(packEnd);

		final byte[] buf = buffer();
		int sz = data.length;
		int len = 0;
		buf[len++] = (byte) ((typeCode << 4) | sz & 15);
		sz >>>= 4;
		while (sz > 0) {
			buf[len - 1] |= 0x80;
			buf[len++] = (byte) (sz & 0x7f);
			sz >>>= 7;
		}

		packDigest.update(buf
		crc.reset();
		crc.update(buf
		buffer(buf

		if (def == null)
			def = new Deflater(Deflater.DEFAULT_COMPRESSION
		else
			def.reset();
		def.setInput(data);
		def.finish();

		while (!def.finished()) {
			len = def.deflate(buf);
			packDigest.update(buf
			crc.update(buf
			buffer(buf
		}

		info.setCRC((int) crc.getValue());
		return true;
	}

	@Override
	protected void onEndThinPack() throws IOException {
		packHash = packDigest.digest();
	}

	private void writePackIndex() throws IOException {
		packDsc.setObjectCount(list.size());

		TemporaryBuffer.Heap buf = null;
		if (list.size() <= 58000) {
			buf = new TemporaryBuffer.Heap(2 * 1024 * 1024);
			index(buf
			packIndex = PackIndex.read(buf.openInputStream());
		}

		DfsOutputStream os = objdb.writePackIndex(packDsc);
		try {
			CountingOutputStream cnt = new CountingOutputStream(os);
			if (buf != null)
				buf.writeTo(cnt
			else
				index(cnt
			packDsc.setIndexSize(cnt.getCount());
		} finally {
			os.close();
		}
	}

	private void index(OutputStream os
			throws IOException {
		PackIndexWriter.createVersion(os
	}
}
