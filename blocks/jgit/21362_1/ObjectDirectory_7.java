		if (unpackedObjectCache.isUnpacked(id)) {
			long len = getLooseObjectSize(curs
			if (0 <= len)
				return len;
		}
		long len = getPackedSize(curs
		if (0 <= len)
			return len;
		return getLooseSize(curs
	}

	private long getPackedSize(WindowCursor curs
		long len = getPackedObjectSize(curs
		if (0 <= len)
			return len;
		for (AlternateHandle alt : myAlternates()) {
			len = alt.db.getPackedSize(curs
			if (0 <= len)
				return len;
		}
		return -1;
	}

	private long getLooseSize(WindowCursor curs
			throws IOException {
		long len = getLooseObjectSize(curs
		if (0 <= len)
			return len;
		for (AlternateHandle alt : myAlternates()) {
			len = alt.db.getLooseSize(curs
			if (0 <= len)
				return len;
		}
		return -1;
	}

	private long getPackedObjectSize(WindowCursor curs
		PackList pList;
		do {
			pList = packList.get();
			SEARCH: for (;;) {
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs
						if (0 <= len)
							return len;
					} catch (PackMismatchException e) {
						PackList nList = scanPacks(pList);
						if (pList != nList) {
							pList = nList;
							continue SEARCH;
						}
					} catch (IOException e) {
						removePack(p);
					}
