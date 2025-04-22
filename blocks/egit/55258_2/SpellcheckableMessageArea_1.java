		final IPropertyChangeListener syntaxColoringChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (JFacePreferences.HYPERLINK_COLOR
						.equals(event.getProperty())) {
					getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!isDisposed()) {
								sourceViewer.refresh();
							}
						}
					});
				}
			}
