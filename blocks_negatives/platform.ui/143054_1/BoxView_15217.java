		propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == PreferenceConstants.PREF_UNDOLIMIT) {
					int limit = UndoPlugin.getDefault().getPreferenceStore()
							.getInt(PreferenceConstants.PREF_UNDOLIMIT);
					getOperationHistory().setLimit(undoContext, limit);
				}
