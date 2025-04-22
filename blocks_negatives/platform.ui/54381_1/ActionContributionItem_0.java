	private final IPropertyChangeListener actionTextListener = new IPropertyChangeListener() {

		/**
		 * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
		 */
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			update(event.getProperty());
		}
	};
