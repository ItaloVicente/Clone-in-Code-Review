	long getObjectSize(WindowCursor curs
		long sz = getObjectSizeWithoutRestoring(curs
		if (0 > sz && restoreFromSelfOrAlternate(id
			sz = getObjectSizeWithoutRestoring(curs
		}
		return sz;
	}

	private long getObjectSizeWithoutRestoring(WindowCursor curs
			AnyObjectId id) throws IOException {
