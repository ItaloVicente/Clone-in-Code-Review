		added = tempAdded;

		Collection<Object> values = map.values();
		ArrayList<DiffEntry> tempDeleted = new ArrayList<DiffEntry>(values
				.size());
		for (Object o : values) {
			if (o instanceof DiffEntry)
				tempDeleted.add((DiffEntry) o);
			else
				tempDeleted.addAll((List<DiffEntry>) o);
