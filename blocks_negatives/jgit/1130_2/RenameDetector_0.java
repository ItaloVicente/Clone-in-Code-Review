		HashMap<AbbreviatedObjectId, Object> map = new HashMap<AbbreviatedObjectId, Object>();
		for (DiffEntry del : deleted) {
			Object old = map.put(del.oldId, del);
			if (old instanceof DiffEntry) {
				ArrayList<DiffEntry> list = new ArrayList<DiffEntry>(2);
				list.add((DiffEntry) old);
				list.add(del);
				map.put(del.oldId, list);
