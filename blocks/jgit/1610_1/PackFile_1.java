		final byte[] delta;
		try {
			delta = new byte[(int) sz];
		} catch (OutOfMemoryError tooBig) {
			return largeDelta(posSelf
		}

		decompress(posSelf + hdrLen
		sz = BinaryDelta.getResultSize(delta);
		if (Integer.MAX_VALUE <= sz)
			return largeDelta(posSelf

		final byte[] result;
		try {
			result = new byte[(int) sz];
		} catch (OutOfMemoryError tooBig) {
			return largeDelta(posSelf
		}

		BinaryDelta.apply(base
		return new ObjectLoader.SmallObject(type
	}

	private LargePackedWholeObject largeWhole(final WindowCursor curs
			final long pos
		return new LargePackedWholeObject(type
	}

	private LargePackedDeltaObject largeDelta(long posObj
			long posBase
		return new LargePackedDeltaObject(posObj
