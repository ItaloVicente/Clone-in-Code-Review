	private IMapChangeListener mapChangeListener = event -> {
		Set affectedElements = event.diff.getChangedKeys();
		LabelProviderChangedEvent newEvent = new LabelProviderChangedEvent(
				ObservableMapCellLabelProvider.this, affectedElements
						.toArray());
		fireLabelProviderChanged(newEvent);
