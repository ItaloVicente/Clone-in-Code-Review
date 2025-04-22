	private IMapChangeListener<K, I> firstMapListener = new IMapChangeListener<K, I>() {

		@Override
		public void handleMapChange(MapChangeEvent<? extends K, ? extends I> event) {
			MapDiff<? extends K, ? extends I> diff = event.diff;
			Set<I> rangeSetAdditions = new HashSet<>();
			Set<I> rangeSetRemovals = new HashSet<>();
			final Set<K> adds = new HashSet<>();
			final Set<K> changes = new HashSet<>();
			final Set<K> removes = new HashSet<>();
			final Map<K, V> oldValues = new HashMap<>();
