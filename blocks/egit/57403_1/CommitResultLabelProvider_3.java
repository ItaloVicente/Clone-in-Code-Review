
	@Override
	public void dispose() {
		Activator.getDefault().getPreferenceStore()
				.removePropertyChangeListener(uiPrefsListener);
		super.dispose();
	}
