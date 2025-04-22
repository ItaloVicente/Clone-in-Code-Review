		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription description = workspace.getDescription();
		description.setAutoBuilding(!description.isAutoBuilding());
		try {
			workspace.setDescription(description);
		} catch (CoreException e) {
			ErrorDialog.openError(window.getShell(), null, null, e.getStatus());
		}
	}
