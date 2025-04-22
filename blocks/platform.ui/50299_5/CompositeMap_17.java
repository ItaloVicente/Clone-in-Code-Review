		public void handleMapChange(MapChangeEvent<K, I> event) {
			MapDiff<K, I> diff = event.diff;
			Set<I> rangeSetAdditions = new HashSet<I>();
			Set<I> rangeSetRemovals = new HashSet<I>();
			final Set<K> adds = new HashSet<K>();
			final Set<K> changes = new HashSet<K>();
			final Set<K> removes = new HashSet<K>();
			final Map<K, V> oldValues = new HashMap<K, V>();

			for (Iterator<K> it = diff.getAddedKeys().iterator(); it.hasNext();) {
				K addedKey = it.next();
				I newValue = diff.getNewValue(addedKey);
