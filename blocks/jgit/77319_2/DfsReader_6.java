		Set<T> pending = new HashSet<>();
		for (T id : objectIds) {
			pending.add(id);
		}

		PackList packList = db.getPackList();
		List<FoundObject<T>> r = new ArrayList<>();
		findAllImpl(packList
		if (packList.dirty()) {
			findAllImpl(db.scanPacks(packList)
		}
		return r;
	}

	private <T extends ObjectId> void findAllImpl(PackList packList
			Set<T> pending
		DfsPackFile[] packs = packList.packs;
		if (packs.length == 0) {
			for (Iterator<T> it = pending.iterator(); it.hasNext();) {
				r.add(new FoundObject<T>(it.next()));
				it.remove();
			}
			return;
