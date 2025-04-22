		String fanOut = id.name().substring(0, 2);
		String[] entries = new File(getDirectory(), fanOut).list();
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
