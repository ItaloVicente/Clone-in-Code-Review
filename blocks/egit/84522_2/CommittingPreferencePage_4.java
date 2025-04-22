		corePreferences = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				org.eclipse.egit.core.Activator.getPluginId());
	}

	@Override
	public void dispose() {
		super.dispose();
		corePreferences = null;
