	private final IPropertyChangeListener uiPrefsListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (UIPreferences.COMMIT_DIALOG_WARN_ABOUT_MESSAGE_SECOND_LINE
					.equals(event.getProperty())) {
				asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!commitMessageSection.isDisposed()) {
							updateMessage();
						}
					}
				});
			}
		}
	};

