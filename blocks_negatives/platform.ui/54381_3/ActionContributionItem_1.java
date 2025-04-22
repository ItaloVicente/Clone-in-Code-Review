	private final IPropertyChangeListener propertyListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			actionPropertyChange(event);
		}
	};
