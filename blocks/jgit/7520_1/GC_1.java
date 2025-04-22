
		RefDatabase refDb = repo.getRefDatabase();
		for (Ref r : refDb.getRefs(RefDatabase.ALL).values()) {
			Storage storage = r.getStorage();
			if (storage == Storage.LOOSE || storage == Storage.LOOSE_PACKED)
				ret.numberOfLooseRefs++;
			if (storage == Storage.PACKED || storage == Storage.LOOSE_PACKED)
				ret.numberOfPackedRefs++;
		}

