		}
		Set removals = new HashSet();
		for (Iterator it2 = event.diff.getRemovals().iterator(); it2.hasNext();) {
			Object removed = it2.next();
			Object mapValue2 = wrappedMap.get(removed);
			if (handleRemoval(mapValue2)) {
				removals.add(mapValue2);
