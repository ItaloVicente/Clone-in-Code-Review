		final Set<K> addedKeys = new HashSet<K>(newMap.keySet());
		final Set<K> removedKeys = new HashSet<K>();
		final Set<K> changedKeys = new HashSet<K>();
		final Map<K, V> oldValues = new HashMap<K, V>();
		final Map<K, V> newValues = new HashMap<K, V>();
		for (Iterator<Entry<K, V>> it = oldMap.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<K, V> oldEntry = it.next();
			K oldKey = oldEntry.getKey();
