
package org.eclipse.jgit.lfs.lib;

import java.io.Serializable;
import java.text.MessageFormat;

import org.eclipse.jgit.lfs.errors.InvalidLongObjectIdException;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public final class AbbreviatedLongObjectId implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final boolean isId(final String id) {
		if (id.length() < 2
				|| Constants.LONG_OBJECT_ID_STRING_LENGTH < id.length())
			return false;
		try {
			for (int i = 0; i < id.length(); i++)
				RawParseUtils.parseHexInt4((byte) id.charAt(i));
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public static final AbbreviatedLongObjectId fromString(final byte[] buf
			final int offset
		if (end - offset > Constants.LONG_OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(MessageFormat.format(
							LfsText.get().invalidLongIdLength
					Integer.valueOf(end - offset)
					Integer.valueOf(Constants.LONG_OBJECT_ID_STRING_LENGTH)));
		return fromHexString(buf
	}

	public static final AbbreviatedLongObjectId fromLongObjectId(
			AnyLongObjectId id) {
		return new AbbreviatedLongObjectId(
				Constants.LONG_OBJECT_ID_STRING_LENGTH
				id.w4);
	}

	public static final AbbreviatedLongObjectId fromString(final String str) {
		if (str.length() > Constants.LONG_OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(
					MessageFormat.format(LfsText.get().invalidLongId
		final byte[] b = org.eclipse.jgit.lib.Constants.encodeASCII(str);
		return fromHexString(b
	}

	private static final AbbreviatedLongObjectId fromHexString(final byte[] bs
			int ptr
		try {
			final long a = hexUInt64(bs
			final long b = hexUInt64(bs
			final long c = hexUInt64(bs
			final long d = hexUInt64(bs
			return new AbbreviatedLongObjectId(end - ptr
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidLongObjectIdException(bs
		}
	}

	private static final long hexUInt64(final byte[] bs
		if (16 <= end - p)
			return RawParseUtils.parseHexInt64(bs

		long r = 0;
		int n = 0;
		while (n < 16 && p < end) {
			r <<= 4;
			r |= RawParseUtils.parseHexInt4(bs[p++]);
			n++;
		}
		return r << (16 - n) * 4;
	}

	static long mask(final int nibbles
		final long b = (word - 1) * 16;
		if (b + 16 <= nibbles) {
			return v;
		}

		if (nibbles <= b) {
			return 0;
		}

		final long s = 64 - (nibbles - b) * 4;
		return (v >>> s) << s;
	}

	final int nibbles;

	final long w1;

	final long w2;

	final long w3;

	final long w4;

	AbbreviatedLongObjectId(final int n
			final long new_3
		nibbles = n;
		w1 = new_1;
		w2 = new_2;
		w3 = new_3;
		w4 = new_4;
	}

	public int length() {
		return nibbles;
	}

	public boolean isComplete() {
		return length() == Constants.LONG_OBJECT_ID_STRING_LENGTH;
	}

	public LongObjectId toLongObjectId() {
		return isComplete() ? new LongObjectId(w1
	}

	public final int prefixCompare(final AnyLongObjectId other) {
		int cmp;

		cmp = NB.compareUInt64(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w3
		if (cmp != 0)
			return cmp;

		return NB.compareUInt64(w4
	}

	public final int prefixCompare(final byte[] bs
		int cmp;

		cmp = NB.compareUInt64(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w3
		if (cmp != 0)
			return cmp;

		return NB.compareUInt64(w4
	}

	public final int prefixCompare(final long[] bs
		int cmp;

		cmp = NB.compareUInt64(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt64(w3
		if (cmp != 0)
			return cmp;

		return NB.compareUInt64(w4
	}

	public final int getFirstByte() {
		return (int) (w1 >>> 56);
	}

	private long mask(final long word
		return mask(nibbles
	}

	@Override
	public int hashCode() {
		return (int) (w2 >>> 32);
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof AbbreviatedLongObjectId) {
			final AbbreviatedLongObjectId b = (AbbreviatedLongObjectId) o;
			return nibbles == b.nibbles && w1 == b.w1 && w2 == b.w2
					&& w3 == b.w3 && w4 == b.w4;
		}
		return false;
	}

	public final String name() {
		final char[] b = new char[Constants.LONG_OBJECT_ID_STRING_LENGTH];

		AnyLongObjectId.formatHexChar(b
		if (nibbles <= 16)
			return new String(b

		AnyLongObjectId.formatHexChar(b
		if (nibbles <= 32)
			return new String(b

		AnyLongObjectId.formatHexChar(b
		if (nibbles <= 48)
			return new String(b

		AnyLongObjectId.formatHexChar(b
		return new String(b
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
	}
}
