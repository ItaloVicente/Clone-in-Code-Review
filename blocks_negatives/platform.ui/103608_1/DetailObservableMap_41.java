	private IMapChangeListener<K, V> detailChangeListener = new IMapChangeListener<K, V>() {
		@Override
		public void handleMapChange(MapChangeEvent<? extends K, ? extends V> event) {
			if (!updating) {
				fireMapChange(Diffs.unmodifiableDiff(event.diff));
			}
