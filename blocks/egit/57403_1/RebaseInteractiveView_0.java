		uiPrefsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)
						|| UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE
								.equals(property)) {
					refresh();
				}
			}
		};

		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(uiPrefsListener);

