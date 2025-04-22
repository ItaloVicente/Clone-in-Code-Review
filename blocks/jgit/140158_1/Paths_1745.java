
package org.eclipse.jgit.util;

public final class NB {
	public static int compareUInt32(final int a
		final int cmp = (a >>> 1) - (b >>> 1);
		if (cmp != 0)
			return cmp;
		return (a & 1) - (b & 1);
	}

	public static int compareUInt64(final long a
		long cmp = (a >>> 1) - (b >>> 1);
		if (cmp > 0) {
			return 1;
		} else if (cmp < 0) {
			return -1;
		}
		cmp = ((a & 1) - (b & 1));
		if (cmp > 0) {
			return 1;
		} else if (cmp < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public static int decodeUInt16(final byte[] intbuf
		int r = (intbuf[offset] & 0xff) << 8;
		return r | (intbuf[offset + 1] & 0xff);
	}

	public static int decodeUInt24(byte[] intbuf
		int r = (intbuf[offset] & 0xff) << 8;
		r |= intbuf[offset + 1] & 0xff;
		return (r << 8) | (intbuf[offset + 2] & 0xff);
	}

	public static int decodeInt32(final byte[] intbuf
		int r = intbuf[offset] << 8;

		r |= intbuf[offset + 1] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 2] & 0xff;
		return (r << 8) | (intbuf[offset + 3] & 0xff);
	}

	public static long decodeInt64(final byte[] intbuf
		long r = intbuf[offset] << 8;

		r |= intbuf[offset + 1] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 2] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 3] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 4] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 5] & 0xff;
		r <<= 8;

		r |= intbuf[offset + 6] & 0xff;
		return (r << 8) | (intbuf[offset + 7] & 0xff);
	}

	public static long decodeUInt32(final byte[] intbuf
		int low = (intbuf[offset + 1] & 0xff) << 8;
		low |= (intbuf[offset + 2] & 0xff);
		low <<= 8;

		low |= (intbuf[offset + 3] & 0xff);
		return ((long) (intbuf[offset] & 0xff)) << 24 | low;
	}

	public static long decodeUInt64(final byte[] intbuf
		return (decodeUInt32(intbuf
				| decodeUInt32(intbuf
	}

	public static void encodeInt16(final byte[] intbuf
		intbuf[offset + 1] = (byte) v;
		v >>>= 8;

		intbuf[offset] = (byte) v;
	}

	public static void encodeInt24(byte[] intbuf
		intbuf[offset + 2] = (byte) v;
		v >>>= 8;

		intbuf[offset + 1] = (byte) v;
		v >>>= 8;

		intbuf[offset] = (byte) v;
	}

	public static void encodeInt32(final byte[] intbuf
		intbuf[offset + 3] = (byte) v;
		v >>>= 8;

		intbuf[offset + 2] = (byte) v;
		v >>>= 8;

		intbuf[offset + 1] = (byte) v;
		v >>>= 8;

		intbuf[offset] = (byte) v;
	}

	public static void encodeInt64(final byte[] intbuf
		intbuf[offset + 7] = (byte) v;
		v >>>= 8;

		intbuf[offset + 6] = (byte) v;
		v >>>= 8;

		intbuf[offset + 5] = (byte) v;
		v >>>= 8;

		intbuf[offset + 4] = (byte) v;
		v >>>= 8;

		intbuf[offset + 3] = (byte) v;
		v >>>= 8;

		intbuf[offset + 2] = (byte) v;
		v >>>= 8;

		intbuf[offset + 1] = (byte) v;
		v >>>= 8;

		intbuf[offset] = (byte) v;
	}

	private NB() {
	}
}
