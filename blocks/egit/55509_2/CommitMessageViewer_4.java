		syntaxColoringListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (JFacePreferences.HYPERLINK_COLOR
						.equals(event.getProperty())) {
					format();
				}
			}
		};
		JFacePreferences.getPreferenceStore()
				.addPropertyChangeListener(syntaxColoringListener);

