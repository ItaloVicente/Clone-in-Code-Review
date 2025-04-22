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
