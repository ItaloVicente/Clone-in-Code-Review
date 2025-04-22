		for (;;) {
			for (PackFile p : pList.packs) {
				try {
					p.resolve(matches
				} catch (IOException e) {
					removePack(p);
				}
				if (matches.size() > RESOLVE_ABBREV_LIMIT)
					return;
