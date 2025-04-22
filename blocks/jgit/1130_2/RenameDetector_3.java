		ArrayList<DiffEntry> uniqueAdds = new ArrayList<DiffEntry>(added.size());
		ArrayList<List<DiffEntry>> nonUniqueAdds = new ArrayList<List<DiffEntry>>();

		for (Object o : addedMap.values()) {
			if (o instanceof DiffEntry)
				uniqueAdds.add((DiffEntry) o);
			else
				nonUniqueAdds.add((List<DiffEntry>) o);
