
package org.eclipse.jgit.internal.storage.file;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_OFS_DELTA;
import static org.eclipse.jgit.lib.Constants.OBJ_REF_DELTA;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
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

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.CountingOutputStream;
import org.eclipse.jgit.util.sha1.SHA1;

public class PackInserter extends ObjectInserter {
	private static final int INDEX_VERSION = 2;

	private final ObjectDirectory db;

	private List<PackedObjectInfo> objectList;
	private ObjectIdOwnerMap<PackedObjectInfo> objectMap;
	private boolean rollback;
	private boolean checkExisting = true;

	private int compression = Deflater.BEST_COMPRESSION;
	private File tmpPack;
	private PackStream packOut;
	private Inflater cachedInflater;

	PackInserter(ObjectDirectory db) {
		this.db = db;
	}

	public void checkExisting(boolean check) {
		checkExisting = check;
	}

	public void setCompressionLevel(int compression) {
		this.compression = compression;
	}

	int getBufferSize() {
		return buffer().length;
	}

	@Override
	public ObjectId insert(int type
			throws IOException {
		ObjectId id = idFor(type
		if (objectMap != null && objectMap.contains(id)) {
			return id;
		}
		if (checkExisting && db.hasPackedObject(id)) {
			return id;
		}

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
		SHA1 md = digest();
		md.update(Constants.encodedTypeString(type));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(len));
		md.update((byte) 0);

		while (0 < len) {
			int n = in.read(buf
			if (n <= 0) {
				throw new EOFException();
			}
			md.update(buf
			packOut.compress.write(buf
			len -= n;
		}
		packOut.compress.finish();
		return endObject(md.toObjectId()
	}

	private long beginObject(int type
		if (packOut == null) {
			beginPack();
		}
		long offset = packOut.getOffset();
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

	private static File idxFor(File packFile) {
		String p = packFile.getName();
		return new File(
				packFile.getParentFile()
				p.substring(0
	}

	private void beginPack() throws IOException {
		objectList = new BlockList<>();
		objectMap = new ObjectIdOwnerMap<>();

		rollback = true;
		tmpPack = File.createTempFile("insert_"
		packOut = new PackStream(tmpPack);

		packOut.write(packOut.hdrBuf
	}

	private static int writePackHeader(byte[] buf
		System.arraycopy(Constants.PACK_SIGNATURE
		NB.encodeInt32(buf
		NB.encodeInt32(buf
		return 12;
	}

	@Override
	public PackParser newPackParser(InputStream in) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectReader newReader() {
		return new Reader();
	}

	@Override
	public void flush() throws IOException {
		if (tmpPack == null) {
			return;
		}

		if (packOut == null) {
			throw new IOException();
		}

		byte[] packHash;
		try {
			packHash = packOut.finishPack();
		} finally {
			packOut = null;
		}

		Collections.sort(objectList);
		File tmpIdx = idxFor(tmpPack);
		writePackIndex(tmpIdx

		File realPack = new File(db.getPackDirectory()
		db.closeAllPackHandles(realPack);
		tmpPack.setReadOnly();
		FileUtils.rename(tmpPack

		File realIdx = idxFor(realPack);
		tmpIdx.setReadOnly();
		try {
			FileUtils.rename(tmpIdx
		} catch (IOException e) {
			File newIdx = new File(
					realIdx.getParentFile()
			try {
				FileUtils.rename(tmpIdx
			} catch (IOException e2) {
				newIdx = tmpIdx;
				e = e2;
			}
			throw new IOException(MessageFormat.format(
					JGitText.get().panicCantRenameIndexFile
					realIdx)
		}

		db.openPack(realPack);
		rollback = false;
		clear();
	}

	private static void writePackIndex(File idx
			List<PackedObjectInfo> list) throws IOException {
		try (OutputStream os = new FileOutputStream(idx)) {
			PackIndexWriter w = PackIndexWriter.createVersion(os
			w.write(list
		}
	}

	private ObjectId computeName(List<PackedObjectInfo> list) {
		SHA1 md = digest().reset();
		byte[] buf = buffer();
		for (PackedObjectInfo otp : list) {
			otp.copyRawTo(buf
			md.update(buf
		}
		return ObjectId.fromRaw(md.digest());
	}

	@Override
	public void close() {
		try {
			if (packOut != null) {
				try {
					packOut.close();
				} catch (IOException err) {
				}
			}
			if (rollback && tmpPack != null) {
				try {
					FileUtils.delete(tmpPack);
				} catch (IOException e) {
				}
				try {
					FileUtils.delete(idxFor(tmpPack));
				} catch (IOException e) {
				}
				rollback = false;
			}
		} finally {
			clear();
			try {
				InflaterCache.release(cachedInflater);
			} finally {
				cachedInflater = null;
			}
		}
	}

	private void clear() {
		objectList = null;
		objectMap = null;
		tmpPack = null;
		packOut = null;
	}

	private Inflater inflater() {
		if (cachedInflater == null) {
			cachedInflater = InflaterCache.get();
		} else {
			cachedInflater.reset();
		}
		return cachedInflater;
	}

	private class PackStream extends OutputStream {
		final byte[] hdrBuf;
		final CRC32 crc32;
		final DeflaterOutputStream compress;

		private final RandomAccessFile file;
		private final CountingOutputStream out;
		private final Deflater deflater;

		private boolean atEnd;

		PackStream(File pack) throws IOException {
			file = new RandomAccessFile(pack
			out = new CountingOutputStream(new FileOutputStream(file.getFD()));
			deflater = new Deflater(compression);
			compress = new DeflaterOutputStream(this
			hdrBuf = new byte[32];
			crc32 = new CRC32();
			atEnd = true;
		}

		long getOffset() {
			return out.getCount();
		}

		void seek(long offset) throws IOException {
			file.seek(offset);
			atEnd = false;
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
			if (!atEnd) {
				file.seek(file.length());
				atEnd = true;
			}
			out.write(data
		}

		byte[] finishPack() throws IOException {
			try {
				file.seek(0);
				out.write(hdrBuf

				byte[] buf = buffer();
				SHA1 md = digest().reset();
				file.seek(0);
				while (true) {
					int r = file.read(buf);
					if (r < 0) {
						break;
					}
					md.update(buf
				}
				byte[] packHash = md.digest();
				out.write(packHash
				return packHash;
			} finally {
				close();
			}
		}

		@Override
		public void close() throws IOException {
			deflater.end();
			try {
				out.close();
			} finally {
				file.close();
			}
		}

		byte[] inflate(long filePos
			byte[] dstbuf;
			try {
				dstbuf = new byte[len];
			} catch (OutOfMemoryError noMemory) {
			}

			byte[] srcbuf = buffer();
			Inflater inf = inflater();
			filePos += setInput(filePos
			for (int dstoff = 0;;) {
				int n = inf.inflate(dstbuf
				dstoff += n;
				if (inf.finished()) {
					return dstbuf;
				}
				if (inf.needsInput()) {
					filePos += setInput(filePos
				} else if (n == 0) {
					throw new DataFormatException();
				}
			}
		}

		private int setInput(long filePos
				throws IOException {
			if (file.getFilePointer() != filePos) {
				seek(filePos);
			}
			int n = file.read(buf);
			if (n < 0) {
				throw new EOFException(JGitText.get().unexpectedEofInPack);
			}
			inf.setInput(buf
			return n;
		}
	}

	private class Reader extends ObjectReader {
		private final ObjectReader ctx;

		private Reader() {
			ctx = db.newReader();
			setStreamFileThreshold(ctx.getStreamFileThreshold());
		}

		@Override
		public ObjectReader newReader() {
			return db.newReader();
		}

		@Override
		public ObjectInserter getCreatedFromInserter() {
			return PackInserter.this;
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			Collection<ObjectId> stored = ctx.resolve(id);
			if (objectList == null) {
				return stored;
			}

			Set<ObjectId> r = new HashSet<>(stored.size() + 2);
			r.addAll(stored);
			for (PackedObjectInfo obj : objectList) {
				if (id.prefixCompare(obj) == 0) {
					r.add(obj.copy());
				}
			}
			return r;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			if (objectMap == null) {
				return ctx.open(objectId
			}

			PackedObjectInfo obj = objectMap.get(objectId);
			if (obj == null) {
				return ctx.open(objectId
			}

			byte[] buf = buffer();
			packOut.seek(obj.getOffset());
			int cnt = packOut.file.read(buf
			if (cnt <= 0) {
				throw new EOFException(JGitText.get().unexpectedEofInPack);
			}

			int c = buf[0] & 0xff;
			int type = (c >> 4) & 7;
			if (type == OBJ_OFS_DELTA || type == OBJ_REF_DELTA) {
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotReadBackDelta
			}
			if (typeHint != OBJ_ANY && type != typeHint) {
				throw new IncorrectObjectTypeException(objectId.copy()
			}

			long sz = c & 0x0f;
			int ptr = 1;
			int shift = 4;
			while ((c & 0x80) != 0) {
				if (ptr >= cnt) {
					throw new EOFException(JGitText.get().unexpectedEofInPack);
				}
				c = buf[ptr++] & 0xff;
				sz += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}

			long zpos = obj.getOffset() + ptr;
			if (sz < getStreamFileThreshold()) {
				byte[] data = inflate(obj
				if (data != null) {
					return new ObjectLoader.SmallObject(type
				}
			}
			return new StreamLoader(type
		}

		private byte[] inflate(PackedObjectInfo obj
				throws IOException
			try {
				return packOut.inflate(zpos
			} catch (DataFormatException dfe) {
				throw new CorruptObjectException(
						MessageFormat.format(
								JGitText.get().objectAtHasBadZlibStream
								Long.valueOf(obj.getOffset())
								tmpPack.getAbsolutePath())
						dfe);
			}
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return ctx.getShallowCommits();
		}

		@Override
		public void close() {
			ctx.close();
		}

		private class StreamLoader extends ObjectLoader {
			private final int type;
			private final long size;
			private final long pos;

			StreamLoader(int type
				this.type = type;
				this.size = size;
				this.pos = pos;
			}

			@Override
			public ObjectStream openStream()
					throws MissingObjectException
				int bufsz = buffer().length;
				packOut.seek(pos);

				InputStream fileStream = new FilterInputStream(
						Channels.newInputStream(packOut.file.getChannel())) {
							@Override
							public int read() throws IOException {
								packOut.atEnd = false;
								return super.read();
							}

							@Override
							public int read(byte[] b) throws IOException {
								packOut.atEnd = false;
								return super.read(b);
							}

							@Override
							public int read(byte[] b
								packOut.atEnd = false;
								return super.read(b
							}

							@Override
							public void close() {
							}
						};
				return new ObjectStream.Filter(
						type
						new BufferedInputStream(
								new InflaterInputStream(fileStream
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
			public byte[] getCachedBytes() throws LargeObjectException {
				throw new LargeObjectException.ExceedsLimit(
						getStreamFileThreshold()
			}
		}
	}
}
