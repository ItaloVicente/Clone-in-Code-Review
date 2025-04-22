		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (final PackFile p : pList.packs) {
				try {
					final ObjectLoader ldr = p.get(curs, objectId);
					if (ldr != null)
						return ldr;
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					removePack(p);
