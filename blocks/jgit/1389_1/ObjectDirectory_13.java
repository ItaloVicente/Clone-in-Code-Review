	void resolve(Set<ObjectId> matches
			throws IOException {
		PackList pList = packList.get();
		if (pList == null)
			pList = scanPacks(pList);
		for (PackFile p : pList.packs) {
			try {
				p.resolve(matches
			} catch (IOException e) {
				removePack(p);
			}
			if (matches.size() > RESOLVE_ABBREV_LIMIT)
				return;
		}

		String fanOut = id.name().substring(0
		String[] entries = new File(getDirectory()
		if (entries != null) {
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
					continue;
				try {
					ObjectId entId = ObjectId.fromString(fanOut + e);
					if (id.prefixCompare(entId) == 0)
						matches.add(entId);
				} catch (IllegalArgumentException notId) {
					continue;
				}
				if (matches.size() > RESOLVE_ABBREV_LIMIT)
					return;
			}
		}

		for (AlternateHandle alt : myAlternates()) {
			alt.db.resolve(matches
			if (matches.size() > RESOLVE_ABBREV_LIMIT)
				return;
		}
	}

