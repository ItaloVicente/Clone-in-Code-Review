	private ISetChangeListener<K> setChangeListener = new ISetChangeListener<K>() {
		@Override
		public void handleSetChange(SetChangeEvent<? extends K> event) {
			Set<K> addedKeys = new HashSet<K>(event.diff.getAdditions());
			Set<K> removedKeys = new HashSet<K>(event.diff.getRemovals());
			Map<K, V> oldValues = new HashMap<>();
			Map<K, V> newValues = new HashMap<>();
			for (Iterator<K> it = removedKeys.iterator(); it.hasNext();) {
				K removedKey = it.next();
				V oldValue = null;
				if (removedKey != null) {
					oldValue = doGet(removedKey);
					unhookListener(removedKey);
					knownKeys.remove(removedKey);
				}
				oldValues.put(removedKey, oldValue);
