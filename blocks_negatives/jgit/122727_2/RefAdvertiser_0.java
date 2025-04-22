	private Iterable<Ref> getSortedRefs(Map<String, Ref> all) {
		if (all instanceof RefMap
				|| (all instanceof SortedMap && ((SortedMap) all).comparator() == null))
			return all.values();
		return RefComparator.sort(all.values());
	}

