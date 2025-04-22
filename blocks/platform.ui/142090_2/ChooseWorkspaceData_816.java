	public String[] getRecentWorkspaces() {
		return recentWorkspaces;
	}

	public void workspaceSelected(String dir) {
		selection = dir;
	}

	public void toggleShowDialog() {
		showDialog = !showDialog;
	}
