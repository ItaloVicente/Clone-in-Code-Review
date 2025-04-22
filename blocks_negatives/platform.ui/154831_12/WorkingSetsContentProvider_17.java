	private IPropertyChangeListener workingSetManagerListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (helper != null) {
				helper.refreshWorkingSetTreeState();
			}
