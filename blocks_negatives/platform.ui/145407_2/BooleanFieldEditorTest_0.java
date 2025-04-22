		bfEditorWithSameLabel.setPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				otherThreadEventOccurred();
			}
		});
