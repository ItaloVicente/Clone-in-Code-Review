    private IPropertyChangeListener enabledWhenListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (addingEvaluationListener) {
				return;
			}
