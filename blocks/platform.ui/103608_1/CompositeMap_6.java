	private IMapChangeListener<I, V> secondMapListener = event -> {
		MapDiff<? extends I, ? extends V> diff = event.diff;
		final Set<K> adds = new HashSet<>();
		final Set<K> changes = new HashSet<>();
		final Set<K> removes = new HashSet<>();
		final Map<K, V> oldValues = new HashMap<>();
		final Map<K, V> newValues = new HashMap<>();
		Set<I> addedKeys = new HashSet<I>(diff.getAddedKeys());
		Set<I> removedKeys = new HashSet<I>(diff.getRemovedKeys());

		for (I addedKey : addedKeys) {
			Set<K> elements1 = firstMap.getKeys(addedKey);
			V newValue1 = diff.getNewValue(addedKey);
			if (pendingChanges.containsKey(addedKey)) {
				I oldKey = pendingChanges.remove(addedKey);
				V oldValue1;
				if (removedKeys.remove(oldKey)) {
					oldValue1 = diff.getOldValue(oldKey);
