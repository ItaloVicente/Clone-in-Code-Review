	private long getPackedObjectSize(WindowCursor curs, AnyObjectId id) {
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs, id);
						p.resetTransientErrorCount();
						if (0 <= len)
							return len;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						handlePackError(e, p);
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return -1;
	}

