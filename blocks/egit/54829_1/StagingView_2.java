	private final IPropertyChangeListener uiPrefsListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (UIPreferences.COMMIT_DIALOG_CHECK_SECOND_LINE
					.equals(event.getProperty())) {
				getSite().getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						updateMessage();
					}
				});
			}
		}
	};

