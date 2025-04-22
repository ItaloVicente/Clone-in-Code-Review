	private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			FrameAction.this.handlePropertyChange(event);
		}
	};
