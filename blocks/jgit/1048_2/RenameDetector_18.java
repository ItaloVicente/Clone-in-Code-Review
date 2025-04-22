		ArrayList<DiffEntry> left = new ArrayList<DiffEntry>(added.size());
		for (DiffEntry dst : added) {
			Object del = map.get(dst.newId);
			if (del instanceof DiffEntry) {
				DiffEntry e = (DiffEntry) del;
				if (sameType(e.oldMode
					if (e.changeType == ChangeType.DELETE) {
						e.changeType = ChangeType.RENAME;
						entries.add(exactRename(e
					} else {
						entries.add(exactCopy(e
					}
				} else {
					left.add(dst);
				}
