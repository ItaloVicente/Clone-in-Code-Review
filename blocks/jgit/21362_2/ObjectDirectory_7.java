		if (unpackedObjectCache.isUnpacked(id)) {
			long len = getLooseObjectSize(curs
			if (0 <= len)
				return len;
		}
		long len = getPackedSizeFromSelfOrAlternate(curs
		if (0 <= len)
			return len;
		return getLooseSizeFromSelfOrAlternate(curs
	}

	private long getPackedSizeFromSelfOrAlternate(WindowCursor curs
			AnyObjectId id) {
		long len = getPackedObjectSize(curs
		if (0 <= len)
			return len;
		for (AlternateHandle alt : myAlternates()) {
			len = alt.db.getPackedSizeFromSelfOrAlternate(curs
			if (0 <= len)
				return len;
		}
		return -1;
	}

	private long getLooseSizeFromSelfOrAlternate(WindowCursor curs
			AnyObjectId id) throws IOException {
		long len = getLooseObjectSize(curs
		if (0 <= len)
			return len;
		for (AlternateHandle alt : myAlternates()) {
			len = alt.db.getLooseSizeFromSelfOrAlternate(curs
			if (0 <= len)
				return len;
		}
		return -1;
	}

	private long getPackedObjectSize(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs
						if (0 <= len)
							return len;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						removePack(p);
					}
