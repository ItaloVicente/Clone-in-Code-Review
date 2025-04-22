		added = left;

		deleted = new ArrayList<DiffEntry>(map.size());
		for (Object o : map.values()) {
			if (o instanceof DiffEntry) {
				DiffEntry e = (DiffEntry) o;
				if (e.changeType == ChangeType.DELETE)
					deleted.add(e);
			} else {
				List<DiffEntry> list = (List<DiffEntry>) o;
				for (DiffEntry e : list) {
					if (e.changeType == ChangeType.DELETE)
						deleted.add(e);
				}
			}
