
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(listener);
		fill = store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL);
		setWrap(store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP));
