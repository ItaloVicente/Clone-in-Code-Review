	private IPropertyChangeListener actionBarPropListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(SubActionBars.P_ACTION_HANDLERS)
					&& activeRec != null
					&& event.getSource() == activeRec.subActionBars) {
				refreshGlobalActionHandlers();
			}
