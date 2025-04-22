		listener = new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(
						UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP)) {
					setWrap(((Boolean) event.getNewValue()).booleanValue());
					return;
				}
				if (event.getProperty().equals(
						UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL)) {
					setFill(((Boolean) event.getNewValue()).booleanValue());
					return;
				}
			}
		};
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(listener);
	}

	@Override
	protected void handleDispose() {
		Activator.getDefault().getPreferenceStore().removePropertyChangeListener(listener);
		super.handleDispose();
