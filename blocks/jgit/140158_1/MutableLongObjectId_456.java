
package org.eclipse.jgit.lfs.lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.eclipse.jgit.lfs.errors.InvalidLongObjectIdException;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class LongObjectId extends AnyLongObjectId implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final LongObjectId ZEROID;

	private static final String ZEROID_STR;

	static {
		ZEROID = new LongObjectId(0L
		ZEROID_STR = ZEROID.name();
	}

	public static final LongObjectId zeroId() {
		return ZEROID;
	}

	public static final boolean isId(String id) {
		if (id.length() != Constants.LONG_OBJECT_ID_STRING_LENGTH)
			return false;
		try {
			for (int i = 0; i < Constants.LONG_OBJECT_ID_STRING_LENGTH; i++) {
				RawParseUtils.parseHexInt4((byte) id.charAt(i));
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public static final String toString(LongObjectId i) {
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
				&& firstBuffer[fi + 19] == secondBuffer[si + 19]
				&& firstBuffer[fi + 20] == secondBuffer[si + 20]
				&& firstBuffer[fi + 21] == secondBuffer[si + 21]
				&& firstBuffer[fi + 22] == secondBuffer[si + 22]
				&& firstBuffer[fi + 23] == secondBuffer[si + 23]
				&& firstBuffer[fi + 24] == secondBuffer[si + 24]
				&& firstBuffer[fi + 25] == secondBuffer[si + 25]
				&& firstBuffer[fi + 26] == secondBuffer[si + 26]
				&& firstBuffer[fi + 27] == secondBuffer[si + 27]
				&& firstBuffer[fi + 28] == secondBuffer[si + 28]
				&& firstBuffer[fi + 29] == secondBuffer[si + 29]
				&& firstBuffer[fi + 30] == secondBuffer[si + 30]
				&& firstBuffer[fi + 31] == secondBuffer[si + 31];
	}

	public static final LongObjectId fromRaw(byte[] bs) {
		return fromRaw(bs
	}

	public static final LongObjectId fromRaw(byte[] bs
		final long a = NB.decodeInt64(bs
		final long b = NB.decodeInt64(bs
		final long c = NB.decodeInt64(bs
		final long d = NB.decodeInt64(bs
		return new LongObjectId(a
	}

	public static final LongObjectId fromRaw(long[] is) {
		return fromRaw(is
	}

	public static final LongObjectId fromRaw(long[] is
		return new LongObjectId(is[p]
	}

	public static final LongObjectId fromString(byte[] buf
		return fromHexString(buf
	}

	public static LongObjectId fromString(String str) {
		if (str.length() != Constants.LONG_OBJECT_ID_STRING_LENGTH)
			throw new InvalidLongObjectIdException(str);
		return fromHexString(org.eclipse.jgit.lib.Constants.encodeASCII(str)
				0);
	}

	private static final LongObjectId fromHexString(byte[] bs
		try {
			final long a = RawParseUtils.parseHexInt64(bs
			final long b = RawParseUtils.parseHexInt64(bs
			final long c = RawParseUtils.parseHexInt64(bs
			final long d = RawParseUtils.parseHexInt64(bs
			return new LongObjectId(a
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidLongObjectIdException(bs
					Constants.LONG_OBJECT_ID_STRING_LENGTH);
		}
	}

	LongObjectId(final long new_1
			final long new_4) {
		w1 = new_1;
		w2 = new_2;
		w3 = new_3;
		w4 = new_4;
	}

	protected LongObjectId(AnyLongObjectId src) {
		w1 = src.w1;
		w2 = src.w2;
		w3 = src.w3;
		w4 = src.w4;
	}

	@Override
	public LongObjectId toObjectId() {
		return this;
	}

	private void writeObject(ObjectOutputStream os) throws IOException {
		os.writeLong(w1);
		os.writeLong(w2);
		os.writeLong(w3);
		os.writeLong(w4);
	}

	private void readObject(ObjectInputStream ois) throws IOException {
		w1 = ois.readLong();
		w2 = ois.readLong();
		w3 = ois.readLong();
		w4 = ois.readLong();
	}
}
