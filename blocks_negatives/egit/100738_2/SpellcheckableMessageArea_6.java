		final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE.equals(event.getProperty())) {
					getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							configureHardWrap();
							if (brokenBidiPlatformTextWidth != -1) {
								layout();
							}
