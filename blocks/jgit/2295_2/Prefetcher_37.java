
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.ChunkFormatter.TRAILER_SIZE;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.util.NB;

public final class PackChunk {
	static final int OBJ_CHUNK_DELTA = 0x10;

	static final int FLAG_FRAGMENTED = 0x40;

	public static class Members {
		private ChunkKey chunkKey;

		private byte[] chunkData;

		private byte[] chunkIndex;

		private ChunkFragments fragments;

		private ChunkPrefetch prefetch;

		public ChunkKey getChunkKey() {
			return chunkKey;
		}

		public Members setChunkKey(ChunkKey key) {
			this.chunkKey = key;
			return this;
		}

		public byte[] getChunkData() {
			return chunkData;
		}

		public Members setChunkData(byte[] chunkData) {
			this.chunkData = chunkData;
			return this;
		}

		public byte[] getChunkIndex() {
			return chunkIndex;
		}

		public Members setChunkIndex(byte[] chunkIndex) {
			this.chunkIndex = chunkIndex;
			return this;
		}

		public ChunkFragments getFragments() {
			return fragments;
		}

		public Members setFragments(ChunkFragments fragments) {
			this.fragments = fragments;
			return this;
		}

		public ChunkPrefetch getPrefetch() {
			return prefetch;
		}

		public Members setPrefetch(ChunkPrefetch prefetch) {
			this.prefetch = prefetch;
			return this;
		}

		public PackChunk build() throws DhtException {
			ChunkIndex i;
			if (chunkIndex != null)
				i = ChunkIndex.fromBytes(chunkKey
			else
				i = null;

			return new PackChunk(chunkKey
		}
	}

	private final ChunkKey key;

	private final byte[] chunk;

	private final ChunkIndex index;

	private final ChunkFragments fragments;

	private final ChunkPrefetch prefetch;

	private volatile Boolean valid;

	private volatile ChunkKey nextFragment;

	PackChunk(ChunkKey key
			ChunkFragments fragments
		this.key = key;
		this.chunk = chunk;
		this.index = index;
		this.fragments = fragments;
		this.prefetch = prefetch;
	}

	public ChunkKey getChunkKey() {
		return key;
	}

	public ChunkIndex getIndex() {
		return index;
	}

	public ChunkFragments getFragments() {
		return fragments;
	}

	public ChunkPrefetch getPrefetch() {
		return prefetch;
	}

	@Override
	public String toString() {
		return "PackChunk[" + getChunkKey() + "]";
	}

	boolean hasIndex() {
		return index != null;
	}

	int findOffset(RepositoryKey repo
		if (key.isFor(repo))
			return index.findOffset(objId);
		return -1;
	}

	boolean contains(RepositoryKey repo
		return 0 <= findOffset(repo
	}

	@SuppressWarnings("null")
	ObjectLoader read(int pos
		try {
			PackChunk pc = this;
			Delta delta = null;
			byte[] data = null;
			int type = Constants.OBJ_BAD;
			boolean cached = false;

			SEARCH: for (;;) {
				if (pc.fragments != null)
					throw new DhtException.TODO("read fragmented objects");

				final byte[] chunkData = pc.chunk;

				int c = chunkData[pos] & 0xff;
				int typeCode = (c >> 4) & 7;
				long sz = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = chunkData[pos + p++] & 0xff;
					sz += (c & 0x7f) << shift;
					shift += 7;
				}
				if (typeCode == Constants.OBJ_EXT)
					typeCode = chunkData[pos + p++] & 0xff;

				switch (typeCode) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG: {
					if (sz < Integer.MAX_VALUE)
						data = pc.decompress(pos + p

					if (delta != null) {
						type = typeCode;
						break SEARCH;
					}

					if (data != null)
						return new ObjectLoader.SmallObject(typeCode
					else
						return new LargeObject(chunkData
				}

				case Constants.OBJ_OFS_DELTA: {
					c = chunkData[pos + p++] & 0xff;
					int base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = chunkData[pos + p++] & 0xff;
						base <<= 7;
						base += (c & 127);
					}
					base = pos - base;
					delta = new Delta(delta
							pc.key
							pc.key
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = delta.getBase(ctx);
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
					ObjectId id = ObjectId.fromRaw(chunkData
					PackChunk nc = pc;
					int base = pc.index.findOffset(id);
					if (base < 0) {
						DhtReader.ChunkAndOffset n = ctx.getChunk(id
						nc = n.chunk;
						base = n.offset;
					}
					delta = new Delta(delta
							pc.key
							nc.getChunkKey()
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = delta.getBase(ctx);
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pc = nc;
					pos = base;
					continue SEARCH;
				}

				case OBJ_CHUNK_DELTA: {
					ChunkKey baseKey = ChunkKey.fromChunk(key
							+ p);
					p += 22;

					c = chunkData[pos + p++] & 0xff;
					int base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = chunkData[pos + p++] & 0xff;
						base <<= 7;
						base += (c & 127);
					}

					delta = new Delta(delta
							pc.key
							baseKey
					if (sz != delta.deltaSize)
						break SEARCH;

					DeltaBaseCache.Entry e = delta.getBase(ctx);
					if (e != null) {
						type = e.type;
						data = e.data;
						cached = true;
						break SEARCH;
					}
					pc = ctx.getChunk(baseKey);
					pos = base;
					continue SEARCH;
				}

				default:
					throw new DhtException(MessageFormat.format(
							DhtText.get().unsupportedObjectTypeInChunk
									.valueOf(typeCode)
									.valueOf(pos)));
				}
			}


			if (data == null)
				return delta.large();

			do {
				pos = delta.deltaPos;

				if (cached)
					cached = false;
				else if (delta.next == null)
					delta.putBase(ctx

				final byte[] cmds = delta.decompress(this
				if (cmds == null) {
					return delta.large();
				}

				final long sz = BinaryDelta.getResultSize(cmds);
				if (Integer.MAX_VALUE <= sz)
					return delta.large();

				final byte[] result;
				try {
					result = new byte[(int) sz];
				} catch (OutOfMemoryError tooBig) {
					return delta.large();
				}

				BinaryDelta.apply(data
				data = result;
				delta = delta.next;
			} while (delta != null);

			return new ObjectLoader.SmallObject(type

		} catch (DataFormatException dfe) {
			CorruptObjectException coe = new CorruptObjectException(
					MessageFormat.format(DhtText.get().corruptCompressedObject
							key
			coe.initCause(dfe);
			throw coe;
		}
	}

	byte[] decompress(int pos
			throws DataFormatException {
		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}


		final Inflater inf = reader.inflater();
		final int stride = 512;
		int dstoff = 0;

		int bs = Math.min(chunk.length - pos
		inf.setInput(chunk
		pos += bs;

		while (dstoff < dstbuf.length) {
			int n = inf.inflate(dstbuf
			if (n == 0) {
				if (inf.needsInput()) {
					bs = Math.min(chunk.length - pos
					inf.setInput(chunk
					pos += bs;
					continue;
				}
				break;
			}
			dstoff += n;
		}

		if (dstoff != sz)
			throw new DataFormatException(MessageFormat.format(
					DhtText.get().shortCompressedObject
							.valueOf(pos)));
		return dstbuf;
	}

	int readObjectTypeAndSize(int ptr
		int c = chunk[ptr++] & 0xff;
		int typeCode = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = chunk[ptr++] & 0xff;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

		if (typeCode == Constants.OBJ_EXT)
			typeCode = chunk[ptr++] & 0xff;

		switch (typeCode) {
		case Constants.OBJ_OFS_DELTA:
			c = chunk[ptr++] & 0xff;
			while ((c & 128) != 0)
				c = chunk[ptr++] & 0xff;
			break;

		case Constants.OBJ_REF_DELTA:
			ptr += 20;
			break;

		case PackChunk.OBJ_CHUNK_DELTA:
			ptr += 22;
			c = chunk[ptr++] & 0xff;
			while ((c & 128) != 0)
				c = chunk[ptr++] & 0xff;
			break;
		}

		info.type = typeCode;
		info.size = sz;
		return ptr;
	}

	int read(int ptr
		int n = Math.min(cnt
		System.arraycopy(chunk
		return n;
	}

	static void copyAsIs(PackChunk pc
			DhtObjectToPack obj
			StoredObjectRepresentationNotAvailableException {
		if (ctx.getOptions().isValidateOnCopyAsIs() && !pc.isValid()) {
			StoredObjectRepresentationNotAvailableException gone;

			gone = new StoredObjectRepresentationNotAvailableException(obj);
			gone.initCause(new DhtException(MessageFormat.format(
					DhtText.get().corruptChunk
			throw gone;
		}

		byte[] chunk = pc.chunk;
		int ptr = obj.offset;
		int c = chunk[ptr++] & 0xff;
		int typeCode = (c >> 4) & 7;
		long inflatedSize = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = chunk[ptr++] & 0xff;
			inflatedSize += (c & 0x7f) << shift;
			shift += 7;
		}

		if (typeCode == Constants.OBJ_EXT)
			typeCode = chunk[ptr++] & 0xff;

		switch (typeCode) {
		case Constants.OBJ_OFS_DELTA:
			do {
				c = chunk[ptr++] & 0xff;
			} while ((c & 128) != 0);
			break;

		case Constants.OBJ_REF_DELTA:
			ptr += 20;
			break;

		case PackChunk.OBJ_CHUNK_DELTA:
			ptr += 22;
			do {
				c = chunk[ptr++] & 0xff;
			} while ((c & 128) != 0);
			break;
		}

		int size = obj.size;
		if (size == 0)
			throw new DhtException(MessageFormat.format(
					DhtText.get().expectedObjectSizeDuringCopyAsIs


		int copyLen = Math.min(size
		out.writeHeader(obj
		out.write(chunk

		ChunkFragments fragments = pc.fragments;
		if (fragments != null) {
			for (int fragId = 1; fragId < fragments.size(); fragId++) {
				pc = ctx.getChunk(fragments.getChunkKey(fragId));
				pc.copyEntireChunkAsIs(out
			}
		}
	}

	private void copyEntireChunkAsIs(PackOutputStream out
			DhtReader ctx) throws IOException {
		if (ctx.getOptions().isValidateOnCopyAsIs() && !isValid()) {
			throw new CorruptObjectException(obj
					.get().corruptChunk
		}

		out.write(chunk
	}

	@SuppressWarnings("boxing")
	private boolean isValid() {
		Boolean v = valid;
		if (v == null) {

			CRC32 crc = new CRC32();
			crc.update(chunk
			int actCRC = (int) crc.getValue();
			int expCRC = NB.decodeInt32(chunk

			if (actCRC == expCRC) {
				MessageDigest m = Constants.newMessageDigest();
				m.update(chunk
				v = key.getChunkHash().compareTo(m.digest()
			} else {
				v = false;
			}
			valid = v;
		}
		return v.booleanValue();
	}

	int getTotalSize() {
		int sz = chunk.length;
		if (index != null)
			sz += index.getIndexSize();
		return sz;
	}

	ChunkKey getNextFragment() {
		if (fragments == null)
			return null;

		ChunkKey next = nextFragment;
		if (next == null)
			nextFragment = next = fragments.getNextFor(getChunkKey());
		return next;
	}

	private static class Delta {
		final Delta next;

		final ChunkKey deltaChunk;

		final int deltaPos;

		final int deltaSize;

		final int hdrLen;

		final ChunkKey baseChunk;

		final int basePos;

		Delta(Delta next
				ChunkKey bc
			this.next = next;
			this.deltaChunk = dc;
			this.deltaPos = ofs;
			this.deltaSize = sz;
			this.hdrLen = hdrLen;
			this.baseChunk = bc;
			this.basePos = bp;
		}

		byte[] decompress(PackChunk chunk
				throws DataFormatException
			if (!deltaChunk.equals(chunk.getChunkKey()))
				chunk = reader.getChunk(deltaChunk);
			return chunk.decompress(deltaPos + hdrLen
		}

		DeltaBaseCache.Entry getBase(DhtReader ctx) {
			return ctx.getDeltaBaseCache().get(baseChunk
		}

		void putBase(DhtReader ctx
			ctx.getDeltaBaseCache().put(baseChunk
		}

		ObjectLoader large() {
			Delta d = this;
			while (d.next != null)
				d = d.next;
			return d.newLargeLoader();
		}

		private ObjectLoader newLargeLoader() {
			throw new DhtException.TODO("large delta support");
		}
	}
}
