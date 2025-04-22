			if (old instanceof DiffEntry) {
				ArrayList<DiffEntry> list = new ArrayList<DiffEntry>(2);
				list.add((DiffEntry) old);
				list.add(del);
				map.put(del.oldId

			} else if (old != null) {
				((List) old).add(del);
				map.put(del.oldId
