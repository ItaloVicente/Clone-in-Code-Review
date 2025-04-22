		PackList packList = db.getPackList();
		resolveImpl(packList
		if (matches.size() < MAX_RESOLVE_MATCHES && packList.dirty()) {
			resolveImpl(db.scanPacks(packList)
		}
		return matches;
	}

	private void resolveImpl(PackList packList
			boolean noGarbage
		for (DfsPackFile pack : packList.packs) {
			if (noGarbage && pack.isGarbage()) {
