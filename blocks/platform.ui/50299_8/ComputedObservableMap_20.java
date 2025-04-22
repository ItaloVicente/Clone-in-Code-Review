		public void handleSetChange(SetChangeEvent<K> event) {
			Set<K> addedKeys = new HashSet<>(event.diff.getAdditions());
			Set<K> removedKeys = new HashSet<>(event.diff.getRemovals());
			Map<K, V> oldValues = new HashMap<>();
			Map<K, V> newValues = new HashMap<>();
			for (Iterator<K> it = removedKeys.iterator(); it.hasNext();) {
				K removedKey = it.next();
				V oldValue = null;
