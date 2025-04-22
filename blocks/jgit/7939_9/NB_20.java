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

