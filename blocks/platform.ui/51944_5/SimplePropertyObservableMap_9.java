		Map<K, V> oldValues = new HashMap<K, V>();
		Map<K, V> newValues = new HashMap<K, V>();
		Set<K> changedKeys = new HashSet<K>();
		Set<K> addedKeys = new HashSet<K>();
		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			K key = entry.getKey();
			V newValue = entry.getValue();
