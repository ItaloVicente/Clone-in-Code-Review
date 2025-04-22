	private IPropertyChangeListener rootModeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if(SHOW_TOP_LEVEL_WORKING_SETS.equals(event.getProperty())) {
				updateRootMode();
			}
