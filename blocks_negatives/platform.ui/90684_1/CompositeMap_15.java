	private IMapChangeListener<I, V> secondMapListener = new IMapChangeListener<I, V>() {

		@Override
		public void handleMapChange(MapChangeEvent<? extends I, ? extends V> event) {
			MapDiff<? extends I, ? extends V> diff = event.diff;
			final Set<K> adds = new HashSet<>();
			final Set<K> changes = new HashSet<>();
			final Set<K> removes = new HashSet<>();
			final Map<K, V> oldValues = new HashMap<>();
			final Map<K, V> newValues = new HashMap<>();
			Set<I> addedKeys = new HashSet<I>(diff.getAddedKeys());
			Set<I> removedKeys = new HashSet<I>(diff.getRemovedKeys());

			for (I addedKey : addedKeys) {
				Set<K> elements = firstMap.getKeys(addedKey);
				V newValue = diff.getNewValue(addedKey);
				if (pendingChanges.containsKey(addedKey)) {
					I oldKey = pendingChanges.remove(addedKey);
					V oldValue;
					if (removedKeys.remove(oldKey)) {
						oldValue = diff.getOldValue(oldKey);
					} else {
						oldValue = secondMap.get(oldKey);
					}
					pendingChanges.remove(oldKey);
					pendingAdds.remove(addedKey);
					pendingRemoves.remove(oldKey);
					for (Iterator<K> it2 = elements.iterator(); it2.hasNext();) {
						K element = it2.next();
						changes.add(element);
						oldValues.put(element, oldValue);
						newValues.put(element, newValue);
						wrappedMap.put(element, newValue);
					}
				} else if (pendingAdds.remove(addedKey)) {
					for (Iterator<K> it2 = elements.iterator(); it2.hasNext();) {
						K element = it2.next();
						adds.add(element);
						newValues.put(element, newValue);
						wrappedMap.put(element, newValue);
					}
