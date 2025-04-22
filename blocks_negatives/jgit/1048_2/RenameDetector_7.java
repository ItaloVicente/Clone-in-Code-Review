			if (old != null) {
				if (old instanceof DiffEntry) {
					ArrayList<DiffEntry> tmp = new ArrayList<DiffEntry>(2);
					tmp.add((DiffEntry) old);
					tmp.add(del);
					map.put(del.oldId, tmp);
				} else {
					((List) old).add(del);
					map.put(del.oldId, old);
				}
