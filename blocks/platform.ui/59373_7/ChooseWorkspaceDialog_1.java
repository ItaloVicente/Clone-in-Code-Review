	private void workspaceSelected(String workspace) {
		launchData.workspaceSelected(TextProcessor.deprocess(workspace));
		super.okPressed();
	}

	private void forgetworkspaceSelected(String workspace) {
		List<String> recentWorkpaces = new ArrayList<>(Arrays.asList(launchData.getRecentWorkspaces()));
		recentWorkpaces.remove(workspace);
		launchData.setRecentWorkspaces(recentWorkpaces.toArray(new String[0]));
		launchData.writePersistedData();
		recentWorkspacesComposites.get(workspace).dispose();
		recentWorkspacesComposites.remove(workspace);
		if (recentWorkspacesComposites.isEmpty()) {
			recentWorkspacesForm.dispose();
		}
		getShell().layout();
		initializeBounds();
		text.remove(workspace);
		if (text.getText().equals(workspace) || text.getText().isEmpty()) {
			text.setText(TextProcessor
					.process((text.getItemCount() > 0 ? text.getItem(0) : launchData.getInitialDefault())));
		}
	}

