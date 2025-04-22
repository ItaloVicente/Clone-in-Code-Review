        private IPropertyChangeListener listener = event -> {
			if (event.getNewValue() != null) {
				fireLabelProviderChanged(new LabelProviderChangedEvent(
						PresentationLabelProvider.this));
			} else {
				refreshAllLabels();
			}
		};
