	private IMapChangeListener<K, V> mapListener = new IMapChangeListener<K, V>() {
		@Override
		public void handleMapChange(MapChangeEvent<? extends K, ? extends V> event) {
			fireMapChange(Diffs.unmodifiableDiff(event.diff));
		}
	};
