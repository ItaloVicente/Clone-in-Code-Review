	@Override
	protected void handleJFacePreferencesChange(PropertyChangeEvent event) {
		if (JFaceResources.TEXT_FONT.equals(event.getProperty())) {
			setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		} else {
			super.handleJFacePreferencesChange(event);
		}
