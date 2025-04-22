	private boolean validateName(String resourceName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus result = workspace.validateName(resourceName, IResource.FILE);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}
		return true;
	}

