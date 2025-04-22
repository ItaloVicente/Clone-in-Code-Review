
package org.eclipse.jgit.lib;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;

public class MutableObjectId extends AnyObjectId {
	public MutableObjectId() {
		super();
	}

	MutableObjectId(MutableObjectId src) {
		fromObjectId(src);
	}

	public void setByte(int index
		switch (index >> 2) {
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
		case 4:
			w5 = set(w5
			break;
		default:
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	private static int set(int w
		value &= 0xff;

		switch (index) {
		case 0:
			return (w & 0x00ffffff) | (value << 24);
		case 1:
			return (w & 0xff00ffff) | (value << 16);
		case 2:
			return (w & 0xffff00ff) | (value << 8);
		case 3:
			return (w & 0xffffff00) | value;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void clear() {
		w1 = 0;
		w2 = 0;
		w3 = 0;
		w4 = 0;
		w5 = 0;
	}

	public void fromObjectId(AnyObjectId src) {
		this.w1 = src.w1;
		this.w2 = src.w2;
		this.w3 = src.w3;
		this.w4 = src.w4;
		this.w5 = src.w5;
	}

	public void fromRaw(byte[] bs) {
		fromRaw(bs
	}

	public void fromRaw(byte[] bs
		w1 = NB.decodeInt32(bs
		w2 = NB.decodeInt32(bs
		w3 = NB.decodeInt32(bs
		w4 = NB.decodeInt32(bs
		w5 = NB.decodeInt32(bs
	}

	public void fromRaw(int[] ints) {
		fromRaw(ints
	}

	public void fromRaw(int[] ints
		w1 = ints[p];
		w2 = ints[p + 1];
		w3 = ints[p + 2];
		w4 = ints[p + 3];
		w5 = ints[p + 4];
	}

	public void set(int a
		w1 = a;
		w2 = b;
		w3 = c;
		w4 = d;
		w5 = e;
	}

	public void fromString(byte[] buf
		fromHexString(buf
	}

	public void fromString(String str) {
		if (str.length() != Constants.OBJECT_ID_STRING_LENGTH)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidId
		fromHexString(Constants.encodeASCII(str)
	}

	private void fromHexString(byte[] bs
		try {
			w1 = RawParseUtils.parseHexInt32(bs
			w2 = RawParseUtils.parseHexInt32(bs
			w3 = RawParseUtils.parseHexInt32(bs
			w4 = RawParseUtils.parseHexInt32(bs
			w5 = RawParseUtils.parseHexInt32(bs
		} catch (ArrayIndexOutOfBoundsException e1) {
			throw new InvalidObjectIdException(bs
					Constants.OBJECT_ID_STRING_LENGTH);
		}
	}

	@Override
	public ObjectId toObjectId() {
		return new ObjectId(this);
	}
}
