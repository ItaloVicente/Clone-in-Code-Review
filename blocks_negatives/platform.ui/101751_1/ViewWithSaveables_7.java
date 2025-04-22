		dirty.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
