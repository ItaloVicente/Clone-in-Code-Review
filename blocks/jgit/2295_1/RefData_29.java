
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.MessageFormat;
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

public class PackChunk {
	static final int OBJ_CHUNK_DELTA = 0x10;

	static final int FLAG_FRAGMENTED = 0x40;

	public static class Builder {
		private ChunkKey chunkKey;

		private byte[] chunkData;

		private byte[] chunkIndex;

		private byte[] fragments;

		public Builder setChunkKey(ChunkKey key) {
			this.chunkKey = key;
			return this;
		}

		public Builder setChunkData(byte[] chunkData) {
			this.chunkData = chunkData;
			return this;
		}

		public Builder setChunkIndex(byte[] chunkIndex) {
			this.chunkIndex = chunkIndex;
			return this;
		}

		public Builder setFragments(byte[] fragments) {
			this.fragments = fragments;
			return this;
		}

		public PackChunk build() throws DhtException {
			ChunkIndex i;
			if (chunkIndex != null)
				i = ChunkIndex.fromRaw(chunkKey
			else
				i = null;

			ChunkFragments f;
			if (fragments != null)
				f = ChunkFragments.fromRaw(fragments);
			else
				f = null;

			return new PackChunk(chunkKey
		}
	}

	private final ChunkKey key;

	private final byte[] chunk;

	private final ChunkIndex index;

	private final ChunkFragments fragments;

	private volatile Boolean valid;

	private volatile ChunkKey nextFragment;

	PackChunk(ChunkKey key
			ChunkFragments fragments) {
		this.key = key;
		this.chunk = chunk;
		this.index = index;
		this.fragments = fragments;
	}

	ChunkKey getChunkKey() {
		return key;
	}

	boolean hasIndex() {
		return index != null;
	}

	@SuppressWarnings("boxing")
	boolean isValid() {
		Boolean v = valid;
		if (v == null) {
			MessageDigest m = Constants.newMessageDigest();
			m.update(chunk);
			v = key.getChunkHash().compareTo(m.digest()
			valid = v;
		}
		return v.booleanValue();
	}

	int findOffset(RepositoryKey repo
		if (key.isFor(repo))
			return index.findOffset(objId);
		return -1;
	}

	boolean contains(RepositoryKey repo
		return 0 <= findOffset(repo
	}

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

				final byte[] chunk = pc.chunk;

				int c = chunk[pos] & 0xff;
				int typeCode = (c >> 4) & 7;
				long sz = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = chunk[pos + p++] & 0xff;
					sz += (c & 0x7f) << shift;
					shift += 7;
				}
				if (typeCode == Constants.OBJ_EXT)
					typeCode = chunk[pos + p++] & 0xff;

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
						return new LargeObject(chunk
				}

				case Constants.OBJ_OFS_DELTA: {
					c = chunk[pos + p++] & 0xff;
					int base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = chunk[pos + p++] & 0xff;
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
					ObjectId id = ObjectId.fromRaw(chunk
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
					p += 22;

					c = chunk[pos + p++] & 0xff;
					int base = c & 127;
					while ((c & 128) != 0) {
						base += 1;
						c = chunk[pos + p++] & 0xff;
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
							typeCode
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

	final byte[] decompress(int pos
			throws DataFormatException {
		byte[] dstbuf;
		try {
			dstbuf = new byte[sz];
		} catch (OutOfMemoryError noMemory) {
			return null;
		}

		int dstoff = 0;
		Inflater inf = reader.inflater();
		inf.setInput(chunk
		for (;;) {
			int n = inf.inflate(dstbuf
			if (n == 0) {
				if (inf.finished())
					break;
				throw new DataFormatException();
			}
			dstoff += n;
		}

		if (dstoff != sz)
			throw new DataFormatException(MessageFormat.format(
					DhtText.get().shortCompressedObject
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

	void copyAsIs(PackOutputStream out
			throws IOException
		if (ctx.getOptions().isValidateOnCopyAsIs() && !isValid()) {
			StoredObjectRepresentationNotAvailableException gone;

			gone = new StoredObjectRepresentationNotAvailableException(obj);
			gone.initCause(new DhtException(MessageFormat.format(
					DhtText.get().corruptChunk
			throw gone;
		}

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

		int size = obj.size;
		if (size == 0)
			throw new DhtException(MessageFormat.format(
					DhtText.get().expectedObjectSizeDuringCopyAsIs

			size = size() - ptr;
		out.writeHeader(obj
		out.write(chunk

		if (fragments != null) {
			for (int fragId = 1; fragId < fragments.size(); fragId++)
				ctx.getChunk(fragments.getChunkKey(fragId)).copyAsIs(out);
		}
	}

	private void copyAsIs(PackOutputStream out) throws IOException {
		out.write(chunk
	}

	int size() {
		return chunk.length;
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

		ObjectLoader large() throws DhtException {
			Delta d = this;
			while (d.next != null)
				d = d.next;
			return d.newLargeLoader();
		}

		private ObjectLoader newLargeLoader() throws DhtException {
			throw new DhtException.TODO("large delta support");
		}
	}
}
