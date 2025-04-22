	private ISetChangeListener<K> setChangeListener = event -> {
		Set<K> addedKeys = new HashSet<K>(event.diff.getAdditions());
		Set<K> removedKeys = new HashSet<K>(event.diff.getRemovals());
		Map<K, V> oldValues = new HashMap<>();
		Map<K, V> newValues = new HashMap<>();
		for (Iterator<K> it1 = removedKeys.iterator(); it1.hasNext();) {
			K removedKey = it1.next();
			V oldValue = null;
			if (removedKey != null) {
				oldValue = doGet(removedKey);
				unhookListener(removedKey);
				knownKeys.remove(removedKey);
