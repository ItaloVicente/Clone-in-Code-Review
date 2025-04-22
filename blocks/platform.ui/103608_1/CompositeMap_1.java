	private IMapChangeListener<K, I> firstMapListener = event -> {
		MapDiff<? extends K, ? extends I> diff = event.diff;
		Set<I> rangeSetAdditions = new HashSet<>();
		Set<I> rangeSetRemovals = new HashSet<>();
		final Set<K> adds = new HashSet<>();
		final Set<K> changes = new HashSet<>();
		final Set<K> removes = new HashSet<>();
		final Map<K, V> oldValues = new HashMap<>();

		for (K addedKey : diff.getAddedKeys()) {
			I newValue1 = diff.getNewValue(addedKey);
			if (!rangeSet.contains(newValue1)) {
				pendingAdds.add(newValue1);
				rangeSetAdditions.add(newValue1);
			} else {
				adds.add(addedKey);
				wrappedMap.put(addedKey, secondMap.get(newValue1));
