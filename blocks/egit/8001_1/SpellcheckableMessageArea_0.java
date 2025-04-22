		final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE.equals(event.getProperty())) {
					getDisplay().asyncExec(new Runnable() {
						public void run() {
							configureHardWrap();
						}
					});
				}
			}
		};
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
