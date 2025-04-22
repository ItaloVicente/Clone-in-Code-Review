		position(oe.getOffset());
		int c = readFrom(Source.FILE);
		final int typeCode = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = readFrom(Source.FILE);
			sz += (c & 0x7f) << shift;
			shift += 7;
		}

		switch (typeCode) {
