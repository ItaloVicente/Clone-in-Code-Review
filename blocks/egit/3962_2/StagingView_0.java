	private int getStagingFormOrientation() {
		boolean columnLayout = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_VIEW_COLUMN_LAYOUT);
		if (columnLayout)
			return SWT.HORIZONTAL;
		else
			return SWT.VERTICAL;
	}

