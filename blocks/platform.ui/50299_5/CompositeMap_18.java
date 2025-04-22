		public void handleMapChange(MapChangeEvent<I, V> event) {
			MapDiff<I, V> diff = event.diff;
			final Set<K> adds = new HashSet<K>();
			final Set<K> changes = new HashSet<K>();
			final Set<K> removes = new HashSet<K>();
			final Map<K, V> oldValues = new HashMap<K, V>();
			final Map<K, V> newValues = new HashMap<K, V>();
			Set<I> addedKeys = new HashSet<I>(diff.getAddedKeys());
			Set<I> removedKeys = new HashSet<I>(diff.getRemovedKeys());

			for (Iterator<I> it = addedKeys.iterator(); it.hasNext();) {
				I addedKey = it.next();
				Set<K> elements = firstMap.getKeys(addedKey);
				V newValue = diff.getNewValue(addedKey);
