		IPreferenceStore ps = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		boolean isShowLocation = ps.getBoolean(IDEInternalPreferences.SHOW_LOCATION);
		boolean isShowFullPath = ps.getBoolean(IDEInternalPreferences.SHOW_LOCATION_FULLPATH);

		if (isShowLocation) {
			if (isShowFullPath) {
				return Platform.getLocation().toOSString();
			}
			String workspaceName = IDEWorkbenchPlugin.getDefault().getPreferenceStore()
					.getString(IDEInternalPreferences.WORKSPACE_NAME);
			if (workspaceName != null && workspaceName.length() > 0) {
				return workspaceName;
			}
