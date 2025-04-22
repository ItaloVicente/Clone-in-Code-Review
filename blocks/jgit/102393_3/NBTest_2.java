	private static byte[] padb(int len
		final byte[] r = new byte[len + 4];
		for (int i = 0; i < len; i++)
			r[i] = (byte) 0xaf;
		r[len] = (byte) a;
		r[len + 1] = (byte) b;
		r[len + 2] = (byte) c;
		return r;
	}

