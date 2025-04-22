	private ISetChangeListener<K> setChangeListener = new ISetChangeListener<K>() {
		@Override
		public void handleSetChange(SetChangeEvent<? extends K> event) {
			Set<K> addedKeys = new HashSet<K>(event.diff.getAdditions());
			Set<K> removedKeys = new HashSet<K>(event.diff.getRemovals());
			Map<K, V> oldValues = new HashMap<>();
			Map<K, V> newValues = new HashMap<>();
			for (K removedKey : removedKeys) {
				V oldValue = null;
				if (removedKey != null) {
					oldValue = doGet(removedKey);
					unhookListener(removedKey);
					knownKeys.remove(removedKey);
				}
				oldValues.put(removedKey, oldValue);
