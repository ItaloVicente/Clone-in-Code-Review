		long sz = getObjectSizeNoRestore(curs
		if (0 > sz && restoreFromSelfOrAlternate(id
			sz = getObjectSizeNoRestore(curs
		}
		return sz;
	}

	private long getObjectSizeNoRestore(WindowCursor curs
			throws IOException {
