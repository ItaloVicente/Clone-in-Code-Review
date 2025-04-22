		PackList packList = db.getPackList();
		if (hasImpl(packList
			return true;
		} else if (packList.dirty()) {
			return hasImpl(db.scanPacks(packList)
		}
		return false;
	}

	private boolean hasImpl(PackList packList
			boolean noGarbage) throws IOException {
		for (DfsPackFile pack : packList.packs) {
