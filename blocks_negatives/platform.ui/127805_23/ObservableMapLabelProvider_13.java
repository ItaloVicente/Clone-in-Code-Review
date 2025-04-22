	private IMapChangeListener mapChangeListener = new IMapChangeListener() {
		@Override
		public void handleMapChange(MapChangeEvent event) {
			Set affectedElements = event.diff.getChangedKeys();
			LabelProviderChangedEvent newEvent = new LabelProviderChangedEvent(
					ObservableMapLabelProvider.this, affectedElements.toArray());
			fireLabelProviderChanged(newEvent);
		}
