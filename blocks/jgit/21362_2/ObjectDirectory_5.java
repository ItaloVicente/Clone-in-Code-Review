	private ObjectLoader openPackedFromSelfOrAlternate(WindowCursor curs
			AnyObjectId objectId) {
		ObjectLoader ldr = openPackedObject(curs
		if (ldr != null)
			return ldr;
		for (AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openPackedFromSelfOrAlternate(curs
			if (ldr != null)
				return ldr;
		}
		return null;
	}

	private ObjectLoader openLooseFromSelfOrAlternate(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		ObjectLoader ldr = openLooseObject(curs
		if (ldr != null)
			return ldr;
		for (AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openLooseFromSelfOrAlternate(curs
			if (ldr != null)
				return ldr;
		}
		return null;
	}

	ObjectLoader openPackedObject(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						ObjectLoader ldr = p.get(curs
						if (ldr != null)
							return ldr;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						removePack(p);
					}
