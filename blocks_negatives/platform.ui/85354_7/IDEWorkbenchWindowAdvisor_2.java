		String workspaceLocation = wbAdvisor.getWorkspaceLocation();
		if (workspaceLocation != null) {
			title = NLS.bind(IDEWorkbenchMessages.WorkbenchWindow_shellTitle,
					title, workspaceLocation);
		}

		String workspaceName = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore().getString(
						IDEInternalPreferences.WORKSPACE_NAME);
		if (workspaceName != null && workspaceName.length() > 0) {
			title = NLS.bind(IDEWorkbenchMessages.WorkbenchWindow_shellTitle,
					workspaceName, title);
