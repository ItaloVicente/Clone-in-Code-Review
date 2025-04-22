
		configuration.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(
						ModelSynchronizeParticipant.P_VISIBLE_MODEL_PROVIDER)) {
					String newValue = (String) event.getNewValue();
					preferenceStore.setValue(
							UIPreferences.SYNC_VIEW_LAST_SELECTED_MODEL,
							newValue);
				}
			}
		});
