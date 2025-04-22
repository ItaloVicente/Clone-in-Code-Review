		if(!forcedShowlocation) {
			store.setValue(IDEInternalPreferences.SHOW_LOCATION, btnShowWorkspacePath.getSelection());
		}
		store.setValue(IDEInternalPreferences.SHOW_LOCATION_NAME, btnShowWorkspaceName.getSelection());
		store.setValue(IDEInternalPreferences.SHOW_PERSPECTIVE_IN_TITLE, btnShowPerspectiveName.getSelection());
		store.setValue(IDEInternalPreferences.SHOW_PRODUCT_IN_TITLE, btnShowProductName.getSelection());
