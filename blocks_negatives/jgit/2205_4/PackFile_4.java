		BinaryDelta.apply(base, delta, result);
		return new ObjectLoader.SmallObject(type, result);
	}

	private LargePackedWholeObject largeWhole(final WindowCursor curs,
			final long pos, final int type, long sz, int p) {
		return new LargePackedWholeObject(type, sz, pos, p, this, curs.db);
	}
