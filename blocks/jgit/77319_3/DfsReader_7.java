		PackList packList = db.getPackList();
		long sz = getObjectSizeImpl(packList
		if (0 <= sz) {
			return sz;
		}
		if (packList.dirty()) {
			sz = getObjectSizeImpl(packList
