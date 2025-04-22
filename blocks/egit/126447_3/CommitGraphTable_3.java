		System.arraycopy(columnLayouts, 0, defaultLayouts, 0,
				columnLayouts.length);
		System.arraycopy(columnLayouts, 0, baseLayouts, 0,
				columnLayouts.length);
		if (useColumnPreferences) {
			IPreferenceStore store = Activator.getDefault()
					.getPreferenceStore();
			applyColumnPreferences(store, rawTable);
			IPropertyChangeListener prefsChanged = event -> {
				String property = event.getProperty();
				if (UIPreferences.HISTORY_COLUMN_ID.equals(property)
						|| UIPreferences.HISTORY_COLUMN_AUTHOR.equals(property)
						|| UIPreferences.HISTORY_COLUMN_AUTHOR_DATE
								.equals(property)
						|| UIPreferences.HISTORY_COLUMN_COMMITTER
								.equals(property)
						|| UIPreferences.HISTORY_COLUMN_COMMITTER_DATE
								.equals(property)) {
					rawTable.getDisplay().asyncExec(() -> {
						if (!rawTable.isDisposed()) {
							applyColumnPreferences(store, rawTable);
							rawTable.getParent().layout();
						}
					});
				}
			};
			store.addPropertyChangeListener(prefsChanged);
			rawTable.addDisposeListener(
					event -> store.removePropertyChangeListener(prefsChanged));
		}
		rawTable.addListener(SWT.Resize, event -> layout(rawTable));
