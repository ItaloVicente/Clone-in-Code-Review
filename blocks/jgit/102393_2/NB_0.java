	public static int decodeUInt24(byte[] intbuf
		int r = intbuf[offset] << 8;
		r |= intbuf[offset + 1] & 0xff;
		return (r << 8) | (intbuf[offset + 2] & 0xff);
	}

