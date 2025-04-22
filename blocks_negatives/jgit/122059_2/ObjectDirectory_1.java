		int oldSize = matches.size();
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					p.resolve(matches, id, RESOLVE_ABBREV_LIMIT);
					p.resetTransientErrorCount();
				} catch (IOException e) {
					handlePackError(e, p);
				}
				if (matches.size() > RESOLVE_ABBREV_LIMIT)
					return;
			}
		} while (matches.size() == oldSize && searchPacksAgain(pList));
