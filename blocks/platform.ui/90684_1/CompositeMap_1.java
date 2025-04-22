	private WritableSetPlus<I> rangeSet = new WritableSetPlus<>();

	private BidiObservableMap<K, I> firstMap;
	private IObservableMap<I, V> secondMap;

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
			}
		}
		for (K changedKey : diff.getChangedKeys()) {
			I oldValue1 = diff.getOldValue(changedKey);
			I newValue2 = diff.getNewValue(changedKey);
			boolean removed = firstMap.getKeys(oldValue1).isEmpty();
			boolean added = !rangeSet.contains(newValue2);
			if (removed) {
				pendingRemoves.put(oldValue1, changedKey);
				rangeSetRemovals.add(oldValue1);
			}
			if (added) {
				pendingAdds.add(newValue2);
				rangeSetAdditions.add(newValue2);
			}
			if (added || removed) {
				pendingChanges.put(oldValue1, newValue2);
				pendingChanges.put(newValue2, oldValue1);
			} else {
				changes.add(changedKey);
				oldValues.put(changedKey, secondMap.get(oldValue1));
				wrappedMap.put(changedKey, secondMap.get(newValue2));
			}
		}
		for (K removedKey : diff.getRemovedKeys()) {
			I oldValue2 = diff.getOldValue(removedKey);
			if (firstMap.getKeys(oldValue2).isEmpty()) {
				pendingRemoves.put(oldValue2, removedKey);
				rangeSetRemovals.add(oldValue2);
			} else {
				removes.add(removedKey);
				oldValues.put(removedKey, secondMap.get(oldValue2));
				wrappedMap.remove(removedKey);
