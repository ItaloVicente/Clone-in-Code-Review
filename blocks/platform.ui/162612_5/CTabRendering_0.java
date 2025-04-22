
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		cornerSize = preferenceService.getBoolean("org.eclipse.ui", USE_ROUND_TABS, //$NON-NLS-1$
				false, null) ? 14 : 0;
	}
