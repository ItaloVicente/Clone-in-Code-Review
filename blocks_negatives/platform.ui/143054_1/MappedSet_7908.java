	private IMapChangeListener mapChangeListener = new IMapChangeListener() {
		@Override
		public void handleMapChange(MapChangeEvent event) {
			MapDiff diff = event.diff;
			Set additions = new HashSet();
			Set removals = new HashSet();
			for (Iterator it = diff.getRemovedKeys().iterator(); it.hasNext();) {
				Object key = it.next();
				Object oldValue = diff.getOldValue(key);
				if (handleRemoval(oldValue)) {
					removals.add(oldValue);
				}
