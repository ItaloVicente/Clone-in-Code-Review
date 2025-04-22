	private IMapChangeListener<Object, Object> mapChangeListener = new IMapChangeListener<Object, Object>() {
		@Override
		public void handleMapChange(MapChangeEvent<?, ?> event) {
			Set<?> affectedElements = event.diff.getChangedKeys();
			LabelProviderChangedEvent newEvent = new LabelProviderChangedEvent(ObservableMapCellLabelProvider.this,
					affectedElements.toArray());
			fireLabelProviderChanged(newEvent);
		}
