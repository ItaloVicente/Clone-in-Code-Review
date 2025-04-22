	private ISetChangeListener domainListener = new ISetChangeListener() {
		@Override
		public void handleSetChange(SetChangeEvent event) {
			Set additions = new HashSet();
			for (Iterator it = event.diff.getAdditions().iterator(); it.hasNext();) {
				Object added = it.next();
				Object mapValue = wrappedMap.get(added);
				if (handleAddition(mapValue)) {
					additions.add(mapValue);
				}
			}
			Set removals = new HashSet();
			for (Iterator it = event.diff.getRemovals().iterator(); it.hasNext();) {
				Object removed = it.next();
				Object mapValue = wrappedMap.get(removed);
				if (handleRemoval(mapValue)) {
					removals.add(mapValue);
				}
