		syntaxColoringListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (JFacePreferences.HYPERLINK_COLOR
						.equals(event.getProperty())) {
					if (!t.isDisposed()) {
						t.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!t.isDisposed()) {
									refresh();
								}
							}
						});
					}
				}
