		PackList packList = db.getPackList();
		resolveImpl(packList
		if (MAX_RESOLVE_MATCHES > matches.size() && packList.dirty()) {
			resolveImpl(db.scanPacks(packList)
		}
		return matches;
	}

	private void resolveImpl(PackList packList
			boolean noGarbage
		if (MAX_RESOLVE_MATCHES <= matches.size()) {
			return;
		}
		for (DfsPackFile pack : packList.packs) {
			if (noGarbage && pack.isGarbage()) {
