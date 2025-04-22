		addField(new BooleanFieldEditor(UIPreferences.REFRESH_ON_INDEX_CHANGE,
				UIText.RefreshPreferencesPage_RefreshWhenIndexChange,
				repoChangeScannerGroup) {
			@Override
			public int getNumberOfControls() {
				return 2;
			}
		});
