		final byte[] ib = curs.tempId;
		readFully(pos, ib, 0, 20, curs);
		int c = ib[0] & 0xff;
		final int type = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		int p = 1;
		while ((c & 0x80) != 0) {
			c = ib[p++] & 0xff;
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

