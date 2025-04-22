		final ByteBuffer bb = Constants.CHARSET.encode(str);
		final int len = bb.limit();
		if (bb.hasArray() && bb.arrayOffset() == 0) {
			final byte[] arr = bb.array();
			if (arr.length == len)
				return arr;
		}
