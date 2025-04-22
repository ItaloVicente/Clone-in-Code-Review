		if (pList == null)
			pList = scanPacks(pList);
		for (PackFile p : pList.packs) {
			try {
				p.resolve(matches, id, RESOLVE_ABBREV_LIMIT);
			} catch (IOException e) {
				removePack(p);
