	private IChangeListener uiChangeListener = new IChangeListener() {
		@Override
		public void handleChange(ChangeEvent event) {
			handleUIChanged();
		}
	};
	private IListChangeListener validationStatusProvidersListener = new IListChangeListener() {
