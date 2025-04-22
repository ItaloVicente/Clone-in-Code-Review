
package org.eclipse.jgit.lib;

import java.io.Serializable;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public final class AbbreviatedObjectId implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final boolean isId(String id) {
		if (id.length() < 2 || Constants.OBJECT_ID_STRING_LENGTH < id.length())
			return false;
		try {
			for (int i = 0; i < id.length(); i++)
				RawParseUtils.parseHexInt4((byte) id.charAt(i));
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public static final AbbreviatedObjectId fromString(final byte[] buf
			final int offset
		if (end - offset > Constants.OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidIdLength
					Integer.valueOf(end - offset)
					Integer.valueOf(Constants.OBJECT_ID_STRING_LENGTH)));
		return fromHexString(buf
	}

	public static final AbbreviatedObjectId fromObjectId(AnyObjectId id) {
		return new AbbreviatedObjectId(Constants.OBJECT_ID_STRING_LENGTH
				id.w1
	}

	public static final AbbreviatedObjectId fromString(String str) {
		if (str.length() > Constants.OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidId
		final byte[] b = Constants.encodeASCII(str);
		return fromHexString(b
	}

	private static final AbbreviatedObjectId fromHexString(final byte[] bs
			int ptr
		try {
			final int a = hexUInt32(bs
			final int b = hexUInt32(bs
			final int c = hexUInt32(bs
			final int d = hexUInt32(bs
			final int e = hexUInt32(bs
			return new AbbreviatedObjectId(end - ptr
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidObjectIdException(bs
		}
	}

	private static final int hexUInt32(final byte[] bs
		if (8 <= end - p)
			return RawParseUtils.parseHexInt32(bs

		int r = 0
		while (n < 8 && p < end) {
			r <<= 4;
			r |= RawParseUtils.parseHexInt4(bs[p++]);
			n++;
		}
		return r << (8 - n) * 4;
	}

	static int mask(int nibbles
		final int b = (word - 1) * 8;
		if (b + 8 <= nibbles) {
			return v;
		}

		if (nibbles <= b) {
			return 0;
		}

		final int s = 32 - (nibbles - b) * 4;
		return (v >>> s) << s;
	}

	final int nibbles;

	final int w1;

	final int w2;

	final int w3;

	final int w4;

	final int w5;

	AbbreviatedObjectId(final int n
			final int new_3
		nibbles = n;
		w1 = new_1;
		w2 = new_2;
		w3 = new_3;
		w4 = new_4;
		w5 = new_5;
	}

	public int length() {
		return nibbles;
	}

	public boolean isComplete() {
		return length() == Constants.OBJECT_ID_STRING_LENGTH;
	}

	public ObjectId toObjectId() {
		return isComplete() ? new ObjectId(w1
	}

	public final int prefixCompare(AnyObjectId other) {
		int cmp;

		cmp = NB.compareUInt32(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w3
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w4
		if (cmp != 0)
			return cmp;

		return NB.compareUInt32(w5
	}

	public final int prefixCompare(byte[] bs
		int cmp;

		cmp = NB.compareUInt32(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w3
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w4
		if (cmp != 0)
			return cmp;

		return NB.compareUInt32(w5
	}

	public final int prefixCompare(int[] bs
		int cmp;

		cmp = NB.compareUInt32(w1
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w2
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w3
		if (cmp != 0)
			return cmp;

		cmp = NB.compareUInt32(w4
		if (cmp != 0)
			return cmp;

		return NB.compareUInt32(w5
	}

	public final int getFirstByte() {
		return w1 >>> 24;
	}

	private int mask(int word
		return mask(nibbles
	}

	@Override
	public int hashCode() {
		return w1;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AbbreviatedObjectId) {
			final AbbreviatedObjectId b = (AbbreviatedObjectId) o;
			return nibbles == b.nibbles && w1 == b.w1 && w2 == b.w2
					&& w3 == b.w3 && w4 == b.w4 && w5 == b.w5;
		}
		return false;
	}

	public final String name() {
		final char[] b = new char[Constants.OBJECT_ID_STRING_LENGTH];

		AnyObjectId.formatHexChar(b
		if (nibbles <= 8)
			return new String(b

		AnyObjectId.formatHexChar(b
		if (nibbles <= 16)
			return new String(b

		AnyObjectId.formatHexChar(b
		if (nibbles <= 24)
			return new String(b

		AnyObjectId.formatHexChar(b
		if (nibbles <= 32)
			return new String(b

		AnyObjectId.formatHexChar(b
		return new String(b
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
	}
}
