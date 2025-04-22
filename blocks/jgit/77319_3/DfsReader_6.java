		PackList packList = db.getPackList();
		List<FoundObject<T>> r = new ArrayList<>();
		findAllImpl(packList
		if (packList.dirty()) {
			findAllImpl(db.scanPacks(packList)
		}
		for (T t : pending) {
			r.add(new FoundObject<T>(t));
		}
		Collections.sort(r
		return r;
	}

	private <T extends ObjectId> void findAllImpl(PackList packList
			Collection<T> pending
		DfsPackFile[] packs = packList.packs;
