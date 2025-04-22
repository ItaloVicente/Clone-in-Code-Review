		Control result = userConfigEditor.createContents();
		Dialog.applyDialogFont(result);
		TabItem userTabItem = new TabItem(tabFolder, SWT.FILL);
		userTabItem.setControl(result);
		userTabItem.setText(
				UIText.GlobalConfigurationPreferencePage_userSettingTabTitle);

		result = sysConfigEditor.createContents();
		Dialog.applyDialogFont(result);
		TabItem sysTabItem = new TabItem(tabFolder, SWT.FILL);
		sysTabItem.setControl(result);
		sysTabItem.setText(
				UIText.GlobalConfigurationPreferencePage_systemSettingTabTitle);

		tabFolder.setSelection(userTabItem);

