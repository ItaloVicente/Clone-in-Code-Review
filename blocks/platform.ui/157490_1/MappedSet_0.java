	private ISetChangeListener domainListener = event -> {
		Set additions = new HashSet();
		for (Iterator it1 = event.diff.getAdditions().iterator(); it1.hasNext();) {
			Object added = it1.next();
			Object mapValue1 = wrappedMap.get(added);
			if (handleAddition(mapValue1)) {
				additions.add(mapValue1);
