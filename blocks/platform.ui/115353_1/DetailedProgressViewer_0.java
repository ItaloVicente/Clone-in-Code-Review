		IPreferenceStore prefs = WorkbenchPlugin.getDefault().getPreferenceStore();
		updateMaxDisplayedValue(prefs);
		IPropertyChangeListener listener = x -> propertyChange(x);
		prefs.addPropertyChangeListener(listener);
		scrolled.addDisposeListener(x -> prefs.removePropertyChangeListener(listener));
	}

	public void propertyChange(PropertyChangeEvent event) {
		if (!IPreferenceConstants.MAX_PROGRESS_ENTRIES.equals(event.getProperty())) {
			return;
		}
		updateMaxDisplayedValue(WorkbenchPlugin.getDefault().getPreferenceStore());
    }

	private void updateMaxDisplayedValue(IPreferenceStore prefs) {
		int newValue = Math.max(1, prefs.getInt(IPreferenceConstants.MAX_PROGRESS_ENTRIES));
		setMaxDisplayed(newValue);
