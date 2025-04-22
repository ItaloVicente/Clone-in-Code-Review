
package org.eclipse.jgit.lfs.lib;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.NB;

public abstract class AnyLongObjectId implements Comparable<AnyLongObjectId> {

	public static boolean equals(final AnyLongObjectId firstObjectId
			final AnyLongObjectId secondObjectId) {
		if (firstObjectId == secondObjectId)
			return true;

		return firstObjectId.w2 == secondObjectId.w2
				&& firstObjectId.w3 == secondObjectId.w3
				&& firstObjectId.w4 == secondObjectId.w4
				&& firstObjectId.w1 == secondObjectId.w1;
	}

	long w1;

	long w2;

	long w3;

	long w4;

	public final int getFirstByte() {
		return (int) (w1 >>> 56);
	}

	public final int getSecondByte() {
		return (int) ((w1 >>> 48) & 0xff);
	}

	public final int getByte(int index) {
		long w;
		switch (index >> 3) {
		case 0:
			w = w1;
			break;
		case 1:
			w = w2;
			break;
		case 2:
			w = w3;
			break;
		case 3:
			w = w4;
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}

		return (int) ((w >>> (8 * (15 - (index & 15)))) & 0xff);
	}

	public final int compareTo(final AnyLongObjectId other) {
		if (this == other)
			return 0;

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

	public final int compareTo(final byte[] bs
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

	public final int compareTo(final long[] bs
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

	public boolean startsWith(final AbbreviatedLongObjectId abbr) {
		return abbr.prefixCompare(this) == 0;
	}

	public final int hashCode() {
		return (int) (w1 >> 32);
	}

	public final boolean equals(final AnyLongObjectId other) {
		return other != null ? equals(this
	}

	public final boolean equals(final Object o) {
		if (o instanceof AnyLongObjectId)
			return equals((AnyLongObjectId) o);
		else
			return false;
	}

	public void copyRawTo(final ByteBuffer w) {
		w.putLong(w1);
		w.putLong(w2);
		w.putLong(w3);
		w.putLong(w4);
	}

	public void copyRawTo(final byte[] b
		NB.encodeInt64(b
		NB.encodeInt64(b
		NB.encodeInt64(b
		NB.encodeInt64(b
	}

	public void copyRawTo(final long[] b
		b[o] = w1;
		b[o + 1] = w2;
		b[o + 2] = w3;
		b[o + 3] = w4;
	}

	public void copyRawTo(final OutputStream w) throws IOException {
		writeRawLong(w
		writeRawLong(w
		writeRawLong(w
		writeRawLong(w
	}

	private static void writeRawLong(final OutputStream w
			throws IOException {
		w.write((int) (v >>> 56));
		w.write((int) (v >>> 48));
		w.write((int) (v >>> 40));
		w.write((int) (v >>> 32));
		w.write((int) (v >>> 24));
		w.write((int) (v >>> 16));
		w.write((int) (v >>> 8));
		w.write((int) v);
	}

	public void copyTo(final OutputStream w) throws IOException {
		w.write(toHexByteArray());
	}

	public void copyTo(byte[] b
		formatHexByte(b
		formatHexByte(b
		formatHexByte(b
		formatHexByte(b
	}

	public void copyTo(ByteBuffer b) {
		b.put(toHexByteArray());
	}

	private byte[] toHexByteArray() {
		final byte[] dst = new byte[Constants.LONG_OBJECT_ID_STRING_LENGTH];
		formatHexByte(dst
		formatHexByte(dst
		formatHexByte(dst
		formatHexByte(dst
		return dst;
	}

	private static final byte[] hexbyte = { '0'
			'7'

	private static void formatHexByte(final byte[] dst
		int o = p + 15;
		while (o >= p && w != 0) {
			dst[o--] = hexbyte[(int) (w & 0xf)];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	public void copyTo(final Writer w) throws IOException {
		w.write(toHexCharArray());
	}

	public void copyTo(final char[] tmp
		toHexCharArray(tmp);
		w.write(tmp
	}

	public void copyTo(final char[] tmp
		toHexCharArray(tmp);
		w.append(tmp
	}

	char[] toHexCharArray() {
		final char[] dst = new char[Constants.LONG_OBJECT_ID_STRING_LENGTH];
		toHexCharArray(dst);
		return dst;
	}

	private void toHexCharArray(final char[] dst) {
		formatHexChar(dst
		formatHexChar(dst
		formatHexChar(dst
		formatHexChar(dst
	}

	private static final char[] hexchar = { '0'
			'7'

	static void formatHexChar(final char[] dst
		int o = p + 15;
		while (o >= p && w != 0) {
			dst[o--] = hexchar[(int) (w & 0xf)];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "AnyLongObjectId[" + name() + "]";
	}

	public final String name() {
		return new String(toHexCharArray());
	}

	public final String getName() {
		return name();
	}

	public AbbreviatedLongObjectId abbreviate(final int len) {
		final long a = AbbreviatedLongObjectId.mask(len
		final long b = AbbreviatedLongObjectId.mask(len
		final long c = AbbreviatedLongObjectId.mask(len
		final long d = AbbreviatedLongObjectId.mask(len
		return new AbbreviatedLongObjectId(len
	}

	public final LongObjectId copy() {
		if (getClass() == LongObjectId.class)
			return (LongObjectId) this;
		return new LongObjectId(this);
	}

	public abstract LongObjectId toObjectId();
}
