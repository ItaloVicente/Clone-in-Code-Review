		public void handleMapChange(MapChangeEvent<K, I> event) {
			MapDiff<K, I> diff = event.diff;
			Set<I> rangeSetAdditions = new HashSet<>();
			Set<I> rangeSetRemovals = new HashSet<>();
			final Set<K> adds = new HashSet<>();
			final Set<K> changes = new HashSet<>();
			final Set<K> removes = new HashSet<>();
			final Map<K, V> oldValues = new HashMap<>();

			for (Iterator<K> it = diff.getAddedKeys().iterator(); it.hasNext();) {
				K addedKey = it.next();
				I newValue = diff.getNewValue(addedKey);
