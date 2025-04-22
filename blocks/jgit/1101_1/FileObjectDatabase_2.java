	long getObjectSize(WindowCursor curs
			throws IOException {
		long sz = getObjectSizeImpl1(curs
		if (0 <= sz)
			return sz;
		return getObjectSizeImpl2(curs
	}

	final long getObjectSizeImpl1(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		long sz;

		sz = getObjectSize1(curs
		if (0 <= sz)
			return sz;

		for (final AlternateHandle alt : myAlternates()) {
			sz = alt.db.getObjectSizeImpl1(curs
			if (0 <= sz)
				return sz;
		}

		if (tryAgain1()) {
			sz = getObjectSize1(curs
			if (0 <= sz)
				return sz;
		}

		return -1;
	}

	final long getObjectSizeImpl2(final WindowCursor curs
			final String objectName
			throws IOException {
		long sz;

		sz = getObjectSize2(curs
		if (0 <= sz)
			return sz;

		for (final AlternateHandle alt : myAlternates()) {
			sz = alt.db.getObjectSizeImpl2(curs
			if (0 <= sz)
				return sz;
		}

		return -1;
	}

