	private ObjectLoader openPacked(WindowCursor curs
		ObjectLoader ldr = openPackedObject(curs
		if (ldr != null)
			return ldr;
		for (AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openPacked(curs
			if (ldr != null)
				return ldr;
		}
		return null;
	}

	private ObjectLoader openLoose(WindowCursor curs
			throws IOException {
		ObjectLoader ldr = openLooseObject(curs
		if (ldr != null)
			return ldr;
		for (AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openLoose(curs
			if (ldr != null)
				return ldr;
		}
		return null;
	}

	ObjectLoader openPackedObject(WindowCursor curs
		PackList pList;
		do {
			pList = packList.get();
			SEARCH: for (;;) {
				for (PackFile p : pList.packs) {
					try {
						ObjectLoader ldr = p.get(curs
						if (ldr != null)
							return ldr;
					} catch (PackMismatchException e) {
						PackList nList = scanPacks(pList);
						if (pList != nList) {
							pList = nList;
							continue SEARCH;
						}
					} catch (IOException e) {
						removePack(p);
					}
