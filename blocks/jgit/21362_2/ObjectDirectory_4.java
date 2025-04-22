		for (AlternateHandle alt : myAlternates()) {
			if (alt.db.hasPackedInSelfOrAlternate(objectId))
				return true;
		}
		return false;
	}

	private boolean hasLooseInSelfOrAlternate(AnyObjectId objectId) {
		if (fileFor(objectId).exists())
			return true;
		for (AlternateHandle alt : myAlternates()) {
			if (alt.db.hasLooseInSelfOrAlternate(objectId))
				return true;
		}
		return false;
	}

	boolean hasPackedObject(AnyObjectId objectId) {
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					if (p.hasObject(objectId))
						return true;
				} catch (IOException e) {
					removePack(p);
