		preferenceChangeListener = new IPropertyChangeListener() {
			@Override
			public final void propertyChange(final PropertyChangeEvent event) {
				if (isChangeImportant(event)) {
					read();
				}
