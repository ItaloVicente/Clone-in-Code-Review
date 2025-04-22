
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

import org.eclipse.jgit.util.NB;

public abstract class AnyObjectId implements Comparable<AnyObjectId> {

	public static boolean equals(final AnyObjectId firstObjectId
			final AnyObjectId secondObjectId) {
		if (firstObjectId == secondObjectId)
			return true;

		return firstObjectId.w3 == secondObjectId.w3
				&& firstObjectId.w4 == secondObjectId.w4
				&& firstObjectId.w5 == secondObjectId.w5
				&& firstObjectId.w1 == secondObjectId.w1
				&& firstObjectId.w2 == secondObjectId.w2;
	}

	int w1;

	int w2;

	int w3;

	int w4;

	int w5;

	public final int getFirstByte() {
		return w1 >>> 24;
	}

	public final int getByte(int index) {
		int w;
		switch (index >> 2) {
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
		case 4:
			w = w5;
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}

		return (w >>> (8 * (3 - (index & 3)))) & 0xff;
	}

	@Override
	public final int compareTo(AnyObjectId other) {
		if (this == other)
			return 0;

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

	public final int compareTo(byte[] bs
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

	public final int compareTo(int[] bs
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

	public boolean startsWith(AbbreviatedObjectId abbr) {
		return abbr.prefixCompare(this) == 0;
	}

	@Override
	public final int hashCode() {
		return w2;
	}

	public final boolean equals(AnyObjectId other) {
		return other != null ? equals(this
	}

	@Override
	public final boolean equals(Object o) {
		if (o instanceof AnyObjectId)
			return equals((AnyObjectId) o);
		else
			return false;
	}

	public void copyRawTo(ByteBuffer w) {
		w.putInt(w1);
		w.putInt(w2);
		w.putInt(w3);
		w.putInt(w4);
		w.putInt(w5);
	}

	public void copyRawTo(byte[] b
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
		NB.encodeInt32(b
	}

	public void copyRawTo(int[] b
		b[o] = w1;
		b[o + 1] = w2;
		b[o + 2] = w3;
		b[o + 3] = w4;
		b[o + 4] = w5;
	}

	public void copyRawTo(OutputStream w) throws IOException {
		writeRawInt(w
		writeRawInt(w
		writeRawInt(w
		writeRawInt(w
		writeRawInt(w
	}

	private static void writeRawInt(OutputStream w
			throws IOException {
		w.write(v >>> 24);
		w.write(v >>> 16);
		w.write(v >>> 8);
		w.write(v);
	}

	public void copyTo(OutputStream w) throws IOException {
		w.write(toHexByteArray());
	}

	public void copyTo(byte[] b
		formatHexByte(b
		formatHexByte(b
		formatHexByte(b
		formatHexByte(b
		formatHexByte(b
	}

	public void copyTo(ByteBuffer b) {
		b.put(toHexByteArray());
	}

	private byte[] toHexByteArray() {
		final byte[] dst = new byte[Constants.OBJECT_ID_STRING_LENGTH];
		formatHexByte(dst
		formatHexByte(dst
		formatHexByte(dst
		formatHexByte(dst
		formatHexByte(dst
		return dst;
	}

	private static final byte[] hexbyte = { '0'
			'7'

	private static void formatHexByte(byte[] dst
		int o = p + 7;
		while (o >= p && w != 0) {
			dst[o--] = hexbyte[w & 0xf];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	public void copyTo(Writer w) throws IOException {
		w.write(toHexCharArray());
	}

	public void copyTo(char[] tmp
		toHexCharArray(tmp);
		w.write(tmp
	}

	public void copyTo(char[] tmp
		toHexCharArray(tmp);
		w.append(tmp
	}

	private char[] toHexCharArray() {
		final char[] dst = new char[Constants.OBJECT_ID_STRING_LENGTH];
		toHexCharArray(dst);
		return dst;
	}

	private void toHexCharArray(char[] dst) {
		formatHexChar(dst
		formatHexChar(dst
		formatHexChar(dst
		formatHexChar(dst
		formatHexChar(dst
	}

	private static final char[] hexchar = { '0'
			'7'

	static void formatHexChar(char[] dst
		int o = p + 7;
		while (o >= p && w != 0) {
			dst[o--] = hexchar[w & 0xf];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "AnyObjectId[" + name() + "]";
	}

	public final String name() {
		return new String(toHexCharArray());
	}

	public final String getName() {
		return name();
	}

	public AbbreviatedObjectId abbreviate(int len) {
		final int a = AbbreviatedObjectId.mask(len
		final int b = AbbreviatedObjectId.mask(len
		final int c = AbbreviatedObjectId.mask(len
		final int d = AbbreviatedObjectId.mask(len
		final int e = AbbreviatedObjectId.mask(len
		return new AbbreviatedObjectId(len
	}

	public final ObjectId copy() {
		if (getClass() == ObjectId.class)
			return (ObjectId) this;
		return new ObjectId(this);
	}

	public abstract ObjectId toObjectId();
}
