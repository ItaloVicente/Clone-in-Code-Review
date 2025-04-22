	private IPropertyChangeListener editorPrefListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			styleViewer();
		}
	};

