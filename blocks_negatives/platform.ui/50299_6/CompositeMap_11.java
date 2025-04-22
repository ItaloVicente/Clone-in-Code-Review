		public void handleMapChange(MapChangeEvent event) {
			MapDiff diff = event.diff;
			final Set adds = new HashSet();
			final Set changes = new HashSet();
			final Set removes = new HashSet();
			final Map oldValues = new HashMap();
			final Map newValues = new HashMap();
			Set addedKeys = new HashSet(diff.getAddedKeys());
			Set removedKeys = new HashSet(diff.getRemovedKeys());

			for (Iterator it = addedKeys.iterator(); it.hasNext();) {
				Object addedKey = it.next();
				Set elements = firstMap.getKeys(addedKey);
				Object newValue = diff.getNewValue(addedKey);
