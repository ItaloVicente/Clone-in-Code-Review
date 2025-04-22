		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (final PackFile p : pList.packs) {
				try {
					long sz = p.getObjectSize(curs, objectId);
					if (0 <= sz)
						return sz;
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					removePack(p);
