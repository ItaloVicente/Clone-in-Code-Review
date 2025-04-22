	private IPropertyChangeListener currentThemeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			firePropertyChange(event);
			if (event.getSource() instanceof FontRegistry) {
				JFaceResources.getFontRegistry().put(event.getProperty(),
						(FontData[]) event.getNewValue());
			} else if (event.getSource() instanceof ColorRegistry) {
				JFaceResources.getColorRegistry().put(event.getProperty(),
						(RGB) event.getNewValue());
			}
