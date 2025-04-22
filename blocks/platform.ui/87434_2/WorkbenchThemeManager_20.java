	private IPropertyChangeListener currentThemeListener = event -> {
		firePropertyChange(event);
		if (event.getSource() instanceof FontRegistry) {
			JFaceResources.getFontRegistry().put(event.getProperty(),
					(FontData[]) event.getNewValue());
		} else if (event.getSource() instanceof ColorRegistry) {
			JFaceResources.getColorRegistry().put(event.getProperty(),
					(RGB) event.getNewValue());
