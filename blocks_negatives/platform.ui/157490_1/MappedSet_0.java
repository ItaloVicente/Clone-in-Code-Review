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
