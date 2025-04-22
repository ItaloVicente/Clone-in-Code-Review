		final byte[] result;
		try {
			result = new byte[(int) sz];
		} catch (OutOfMemoryError tooBig) {
			return largeDelta(posSelf, hdrLen, posBase, curs);
		}
