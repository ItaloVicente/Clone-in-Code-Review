		Composite composite = SWTUtils.createHVFillComposite(parent,
				SWTUtils.MARGINS_NONE);
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayoutData(SWTUtils.createHVFillGridData());
		userConfigEditor = new ConfigurationEditorComponent(tabFolder, userConfig, true) {
			@Override
			protected void setErrorMessage(String message) {
				GlobalConfigurationPreferencePage.this.setErrorMessage(message);
			}

			@Override
			protected void setDirty(boolean dirty) {
				userIsDirty = dirty;
				updateApplyButton();
			}
		};
		sysConfigEditor = new ConfigurationEditorComponent(tabFolder, sysConfig, true) {
