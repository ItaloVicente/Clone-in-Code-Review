
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.eclipse.jgit.storage.dht.TinyProtobuf.encode;

import java.util.Arrays;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.dht.TinyProtobuf.Encoder;

public class RefData {
	public static final RefData NONE = new RefData(new byte[0]);

	static final int TAG_SYMREF = 1;

	static final int TAG_TARGET = 2;

	static final int TAG_IS_PEELED = 3;

	static final int TAG_PEELED = 4;

	public static RefData fromBytes(byte[] data) {
		return new RefData(data);
	}

	static RefData symbolic(String target) {
		Encoder e = encode(2 + target.length());
		e.string(TAG_SYMREF
		return new RefData(e.asByteArray());
	}

	static RefData id(AnyObjectId id) {
		Encoder e = encode(4 + OBJECT_ID_STRING_LENGTH + ChunkKey.KEYLEN);
		e.message(TAG_TARGET
		return new RefData(e.asByteArray());
	}

	static RefData fromRef(Ref ref) {
		if (ref.isSymbolic())
			return symbolic(ref.getTarget().getName());

		if (ref.getObjectId() == null)
			return RefData.NONE;

		int max = 8 + 2 * OBJECT_ID_STRING_LENGTH + 2 * ChunkKey.KEYLEN;
		Encoder e = encode(max);
		e.message(TAG_TARGET
		if (ref.isPeeled()) {
			e.bool(TAG_IS_PEELED
			if (ref.getPeeledObjectId() != null)
				e.message(TAG_PEELED
						IdWithChunk.encode(ref.getPeeledObjectId()));
		}
		return new RefData(e.asByteArray());
	}

	static RefData peeled(ObjectId targetId
		int max = 8 + 2 * OBJECT_ID_STRING_LENGTH + 2 * ChunkKey.KEYLEN;
		Encoder e = encode(max);
		e.message(TAG_TARGET
		e.bool(TAG_IS_PEELED
		if (peeledId != null)
			e.message(TAG_PEELED
		return new RefData(e.asByteArray());
	}

	private final byte[] data;

	RefData(byte[] data) {
		this.data = data;
	}

	TinyProtobuf.Decoder decode() {
		return TinyProtobuf.decode(data);
	}

	public byte[] asBytes() {
		return data;
	}

	@Override
	public int hashCode() {
		int hash = 5381;
		for (int ptr = 0; ptr < data.length; ptr++)
			hash = ((hash << 5) + hash) + (data[ptr] & 0xff);
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof RefData)
			return Arrays.equals(data
		return false;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		TinyProtobuf.Decoder d = decode();
		for (;;) {
			switch (d.next()) {
			case 0:
				return b.toString().substring(1);
			case TAG_SYMREF:
				b.append("\nsymref: ").append(d.string());
				continue;
			case TAG_TARGET:
				b.append("\ntarget: ").append(IdWithChunk.decode(d.message()));
				continue;
			case TAG_IS_PEELED:
				b.append("\nis_peeled: ").append(d.bool());
				continue;
			case TAG_PEELED:
				b.append("\npeeled: ").append(IdWithChunk.decode(d.message()));
				continue;
			default:
				d.skip();
				continue;
			}
		}
	}

	static class IdWithChunk extends ObjectId {
		static ObjectId decode(TinyProtobuf.Decoder d) {
			ObjectId id = null;
			ChunkKey key = null;
			DECODE: for (;;) {
				switch (d.next()) {
				case 0:
					break DECODE;
				case 1:
					id = d.stringObjectId();
					continue;
				case 2:
					key = ChunkKey.fromBytes(d.bytes());
					continue;
				default:
					d.skip();
				}
			}
			return key != null ? new IdWithChunk(id
		}

		static TinyProtobuf.Encoder encode(AnyObjectId id) {
			if (id instanceof IdWithChunk) {
				int max = 4 + OBJECT_ID_STRING_LENGTH + ChunkKey.KEYLEN;
				TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
				e.string(1
				e.string(2
				return e;
			} else {
				int max = 2 + OBJECT_ID_STRING_LENGTH;
				TinyProtobuf.Encoder e = TinyProtobuf.encode(max);
				e.string(1
				return e;
			}
		}

		private final ChunkKey chunkKey;

		IdWithChunk(AnyObjectId id
			super(id);
			this.chunkKey = key;
		}

		ChunkKey getChunkKey() {
			return chunkKey;
		}

		@Override
		public String toString() {
			return name() + "->" + chunkKey;
		}
	}
}
