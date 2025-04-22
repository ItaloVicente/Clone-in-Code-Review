	public void preferenceChange(PreferenceChangeEvent event) {
		if (!USE_ROUND_TABS.equals(event.getKey())) {
			return;
		}
		cornerRadiusPreferenceChanged();
	}

	private IEclipsePreferences getSwtRendererPreferences() {
		return InstanceScope.INSTANCE.getNode("org.eclipse.e4.ui.workbench.renderers.swt"); //$NON-NLS-1$
