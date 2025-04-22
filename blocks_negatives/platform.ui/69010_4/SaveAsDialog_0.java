        IPath fullPath = resourceGroup.getContainerFullPath();
        if (fullPath != null) {
        	String projectName = fullPath.segment(0);
	        IStatus isValidProjectName = workspace.validateName(projectName, IResource.PROJECT);
	        if(isValidProjectName.isOK()) {
	        	IProject project = workspace.getRoot().getProject(projectName);
	        	if(!project.isOpen()) {
	        		setErrorMessage(IDEWorkbenchMessages.SaveAsDialog_closedProjectMessage);
	        		return false;
	        	}
	        }
        }

        IStatus result = workspace.validateName(resourceName, IResource.FILE);
