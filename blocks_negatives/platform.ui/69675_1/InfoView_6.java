	private IPropertyChangeListener personListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (IPersonService.PROP_CHANGE.equals(event.getProperty())) {
				refresh();
			} else if (IPersonService.PROP_ADD.equals(event.getProperty())) {
				viewerInput.add(event.getNewValue());
				viewer.add(event.getNewValue());
			}
