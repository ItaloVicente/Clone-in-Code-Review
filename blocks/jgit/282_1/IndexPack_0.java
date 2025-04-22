
	Set<ObjectId> getEntries() {
		Set<ObjectId> result = new HashSet<ObjectId>();
		for (int i = 0; i < initialEntryCount; i++) {
			result.add(entries[i]);
		}
		return Collections.unmodifiableSet(result);
	}

	Set<ObjectId> getBaseIds() {
		if (baseIds == null) {
			Iterator<DeltaChain> iter = baseById.iterator();
			Set<ObjectId> result = new HashSet<ObjectId>();
			while (iter.hasNext()) {
				result.add(iter.next());
			}
			baseIds = Collections.unmodifiableSet(result);
		}
		return baseIds;
	}
