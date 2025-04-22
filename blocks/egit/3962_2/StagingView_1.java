				.getBoolean(UIPreferences.STAGING_VIEW_SHOW_NEW_COMMITS));
		columnLayoutAction = new Action(UIText.StagingView_ColumnLayout,
				IAction.AS_CHECK_BOX) {

			public void run() {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(UIPreferences.STAGING_VIEW_COLUMN_LAYOUT,
								isChecked());
				stagingSashForm.setOrientation(isChecked() ? SWT.HORIZONTAL
						: SWT.VERTICAL);
			}
		};
		columnLayoutAction.setChecked(Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_VIEW_COLUMN_LAYOUT));

