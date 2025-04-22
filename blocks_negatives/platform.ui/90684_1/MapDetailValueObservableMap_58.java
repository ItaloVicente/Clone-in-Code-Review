	private IMapChangeListener<K, M> masterMapListener = new IMapChangeListener<K, M>() {
		@Override
		public void handleMapChange(MapChangeEvent<? extends K, ? extends M> event) {
			handleMasterMapChange(event.diff);
		}
	};
