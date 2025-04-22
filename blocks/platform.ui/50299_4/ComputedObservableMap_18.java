		public void handleSetChange(SetChangeEvent<K> event) {
			Set<K> addedKeys = new HashSet<K>(event.diff.getAdditions());
			Set<K> removedKeys = new HashSet<K>(event.diff.getRemovals());
			Map<K, V> oldValues = new HashMap<K, V>();
			Map<K, V> newValues = new HashMap<K, V>();
			for (Iterator<K> it = removedKeys.iterator(); it.hasNext();) {
				K removedKey = it.next();
				V oldValue = null;
