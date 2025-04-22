
package org.eclipse.jgit.lfs.lib;

import java.text.MessageFormat;

import org.eclipse.jgit.lfs.errors.InvalidLongObjectIdException;
import org.eclipse.jgit.lfs.internal.LfsText;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class MutableLongObjectId extends AnyLongObjectId {
	public MutableLongObjectId() {
		super();
	}

	MutableLongObjectId(MutableLongObjectId src) {
		fromObjectId(src);
	}

	public void setByte(int index
		switch (index >> 3) {
		case 0:
			w1 = set(w1
			break;
		case 1:
			w2 = set(w2
			break;
		case 2:
			w3 = set(w3
			break;
		case 3:
			w4 = set(w4
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	private static long set(long w
		value &= 0xff;

		switch (index) {
		case 0:
			return (w & 0x00ffffffffffffffL) | (value << 56);
		case 1:
			return (w & 0xff00ffffffffffffL) | (value << 48);
		case 2:
			return (w & 0xffff00ffffffffffL) | (value << 40);
		case 3:
			return (w & 0xffffff00ffffffffL) | (value << 32);
		case 4:
			return (w & 0xffffffff00ffffffL) | (value << 24);
		case 5:
			return (w & 0xffffffffff00ffffL) | (value << 16);
		case 6:
			return (w & 0xffffffffffff00ffL) | (value << 8);
		case 7:
			return (w & 0xffffffffffffff00L) | value;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void clear() {
		w1 = 0;
		w2 = 0;
		w3 = 0;
		w4 = 0;
	}

	public void fromObjectId(AnyLongObjectId src) {
		this.w1 = src.w1;
		this.w2 = src.w2;
		this.w3 = src.w3;
		this.w4 = src.w4;
	}

	public void fromRaw(final byte[] bs) {
		fromRaw(bs
	}

	public void fromRaw(final byte[] bs
		w1 = NB.decodeInt64(bs
		w2 = NB.decodeInt64(bs
		w3 = NB.decodeInt64(bs
		w4 = NB.decodeInt64(bs
	}

	public void fromRaw(final long[] longs) {
		fromRaw(longs
	}

	public void fromRaw(final long[] longs
		w1 = longs[p];
		w2 = longs[p + 1];
		w3 = longs[p + 2];
		w4 = longs[p + 3];
	}

	public void fromString(final byte[] buf
		fromHexString(buf
	}

	public void fromString(final String str) {
		if (str.length() != Constants.LONG_OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(
					MessageFormat.format(LfsText.get().invalidLongId
		fromHexString(org.eclipse.jgit.lib.Constants.encodeASCII(str)
	}

	private void fromHexString(final byte[] bs
		try {
			w1 = RawParseUtils.parseHexInt64(bs
			w2 = RawParseUtils.parseHexInt64(bs
			w3 = RawParseUtils.parseHexInt64(bs
			w4 = RawParseUtils.parseHexInt64(bs
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidLongObjectIdException(bs
					Constants.LONG_OBJECT_ID_STRING_LENGTH);
		}
	}

	@Override
	public LongObjectId toObjectId() {
		return new LongObjectId(this);
	}
}
