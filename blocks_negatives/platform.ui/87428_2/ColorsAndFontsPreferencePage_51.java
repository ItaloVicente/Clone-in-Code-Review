        private IPropertyChangeListener listener = new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getNewValue() != null) {
					fireLabelProviderChanged(new LabelProviderChangedEvent(
							PresentationLabelProvider.this));
				} else {
					refreshAllLabels();
				}
            }
        };
