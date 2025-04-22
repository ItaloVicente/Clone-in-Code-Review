		IPropertyChangeListener uiPrefsListener = (event) -> {
			String property = event.getProperty();
			if (UIPreferences.DATE_FORMAT.equals(property)
					|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)) {
				setPerson(userText, person, author);
				userText.requestLayout();
			}
		};
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(uiPrefsListener);
		userText.addDisposeListener((e) -> {
			Activator.getDefault().getPreferenceStore()
					.removePropertyChangeListener(uiPrefsListener);
		});
