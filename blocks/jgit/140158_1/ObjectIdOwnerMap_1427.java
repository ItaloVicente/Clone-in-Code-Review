
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class ObjectId extends AnyObjectId implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final ObjectId ZEROID;

	private static final String ZEROID_STR;

	static {
		ZEROID = new ObjectId(0
		ZEROID_STR = ZEROID.name();
	}

	public static final ObjectId zeroId() {
		return ZEROID;
	}

	public static final boolean isId(@Nullable String id) {
		if (id == null) {
			return false;
		}
		if (id.length() != Constants.OBJECT_ID_STRING_LENGTH)
			return false;
		try {
			for (int i = 0; i < Constants.OBJECT_ID_STRING_LENGTH; i++) {
				RawParseUtils.parseHexInt4((byte) id.charAt(i));
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public static final String toString(ObjectId i) {
		return i != null ? i.name() : ZEROID_STR;
	}

	public static boolean equals(final byte[] firstBuffer
			final byte[] secondBuffer
		return firstBuffer[fi] == secondBuffer[si]
				&& firstBuffer[fi + 1] == secondBuffer[si + 1]
				&& firstBuffer[fi + 2] == secondBuffer[si + 2]
				&& firstBuffer[fi + 3] == secondBuffer[si + 3]
				&& firstBuffer[fi + 4] == secondBuffer[si + 4]
				&& firstBuffer[fi + 5] == secondBuffer[si + 5]
				&& firstBuffer[fi + 6] == secondBuffer[si + 6]
				&& firstBuffer[fi + 7] == secondBuffer[si + 7]
				&& firstBuffer[fi + 8] == secondBuffer[si + 8]
				&& firstBuffer[fi + 9] == secondBuffer[si + 9]
				&& firstBuffer[fi + 10] == secondBuffer[si + 10]
				&& firstBuffer[fi + 11] == secondBuffer[si + 11]
				&& firstBuffer[fi + 12] == secondBuffer[si + 12]
				&& firstBuffer[fi + 13] == secondBuffer[si + 13]
				&& firstBuffer[fi + 14] == secondBuffer[si + 14]
				&& firstBuffer[fi + 15] == secondBuffer[si + 15]
				&& firstBuffer[fi + 16] == secondBuffer[si + 16]
				&& firstBuffer[fi + 17] == secondBuffer[si + 17]
				&& firstBuffer[fi + 18] == secondBuffer[si + 18]
				&& firstBuffer[fi + 19] == secondBuffer[si + 19];
	}

	public static final ObjectId fromRaw(byte[] bs) {
		return fromRaw(bs
	}

	public static final ObjectId fromRaw(byte[] bs
		final int a = NB.decodeInt32(bs
		final int b = NB.decodeInt32(bs
		final int c = NB.decodeInt32(bs
		final int d = NB.decodeInt32(bs
		final int e = NB.decodeInt32(bs
		return new ObjectId(a
	}

	public static final ObjectId fromRaw(int[] is) {
		return fromRaw(is
	}

	public static final ObjectId fromRaw(int[] is
		return new ObjectId(is[p]
	}

	public static final ObjectId fromString(byte[] buf
		return fromHexString(buf
	}

	public static ObjectId fromString(String str) {
		if (str.length() != Constants.OBJECT_ID_STRING_LENGTH) {
			throw new InvalidObjectIdException(str);
		}
		return fromHexString(Constants.encodeASCII(str)
	}

	private static final ObjectId fromHexString(byte[] bs
		try {
			final int a = RawParseUtils.parseHexInt32(bs
			final int b = RawParseUtils.parseHexInt32(bs
			final int c = RawParseUtils.parseHexInt32(bs
			final int d = RawParseUtils.parseHexInt32(bs
			final int e = RawParseUtils.parseHexInt32(bs
			return new ObjectId(a
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidObjectIdException(bs
					Constants.OBJECT_ID_STRING_LENGTH);
		}
	}

	public ObjectId(int new_1
		w1 = new_1;
		w2 = new_2;
		w3 = new_3;
		w4 = new_4;
		w5 = new_5;
	}

	protected ObjectId(AnyObjectId src) {
		w1 = src.w1;
		w2 = src.w2;
		w3 = src.w3;
		w4 = src.w4;
		w5 = src.w5;
	}

	@Override
	public ObjectId toObjectId() {
		return this;
	}

	private void writeObject(ObjectOutputStream os) throws IOException {
		os.writeInt(w1);
		os.writeInt(w2);
		os.writeInt(w3);
		os.writeInt(w4);
		os.writeInt(w5);
	}

	private void readObject(ObjectInputStream ois) throws IOException {
		w1 = ois.readInt();
		w2 = ois.readInt();
		w3 = ois.readInt();
		w4 = ois.readInt();
		w5 = ois.readInt();
	}
}
