		final byte[] delta;
		try {
			delta = new byte[(int) sz];
		} catch (OutOfMemoryError tooBig) {
			return largeDelta(posSelf, hdrLen, posBase, curs);
		}
