		if (ps.getBoolean(IDEInternalPreferences.SHOW_LOCATION_NAME)) {
			String workspaceName = IDEWorkbenchPlugin.getDefault().getPreferenceStore()
					.getString(IDEInternalPreferences.WORKSPACE_NAME);
			if (workspaceName != null && workspaceName.length() > 0) {
				sj.add(workspaceName);
			}
		}

		if (ps.getBoolean(IDEInternalPreferences.SHOW_PERSPECTIVE_IN_TITLE)) {
			IPerspectiveDescriptor persp = currentPage.getPerspective();
			if (persp != null) {
				sj.add(persp.getLabel());
			}
		}

		String workspaceLocation = wbAdvisor.getWorkspaceLocation();
		if (workspaceLocation != null) {
			sj.add(workspaceLocation);
		}

