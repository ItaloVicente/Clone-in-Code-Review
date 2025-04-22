		ldr = openImpl(packList
		if (ldr != null) {
			return ldr;
		}
		if (packList.dirty()) {
			ldr = openImpl(db.scanPacks(packList)
