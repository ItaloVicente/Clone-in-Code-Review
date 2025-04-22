	@Override
	public void dispose() {
		super.dispose();
		util.getPreferences().removePreferenceChangeListener(
				configChangeListener);
	}
