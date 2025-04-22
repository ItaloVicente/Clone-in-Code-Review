		uiPrefsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)) {
					changeSetPreview.refresh();
				}
			}
		};
		getPreferenceStore().addPropertyChangeListener(uiPrefsListener);

