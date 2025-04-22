    private IPropertyChangeListener listener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!hasOverrideFor(event.getProperty()))
				fireMappingChanged(event.getProperty(), event.getOldValue(),
						event.getNewValue());
        }
    };
