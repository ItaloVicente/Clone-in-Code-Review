		final IPropertyChangeListener propertyChangeListener = event -> {
			if (UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE
					.equals(event.getProperty())) {
				getDisplay().asyncExec(() -> {
					if (!isDisposed()) {
						configureHardWrap();
						if (brokenBidiPlatformTextWidth != -1) {
							layout();
