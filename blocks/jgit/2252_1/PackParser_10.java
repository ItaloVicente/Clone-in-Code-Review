	public List<PackedObjectInfo> getSortedObjectList() {
		Arrays.sort(entries
		List<PackedObjectInfo> list = Arrays.asList(entries);
		if (entryCount < entries.length)
			list = list.subList(0
		return list;
	}

