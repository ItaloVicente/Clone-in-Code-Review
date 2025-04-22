
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		cornerSize = preferenceService.getBoolean("org.eclipse.ui", "USE_SQUARE_TABS", //$NON-NLS-1$ //$NON-NLS-2$
				false, null) ? 0 : 14;
	}
