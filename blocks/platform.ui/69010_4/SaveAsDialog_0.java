		IPath fullPath = resourceGroup.getContainerFullPath().append(resourceGroup.getResource());
		String projectName = fullPath.segment(0);
		IStatus isValidProjectName = workspace.validateName(projectName, IResource.PROJECT);
		if (isValidProjectName.isOK()) {
			IProject project = workspace.getRoot().getProject(projectName);
			if (!project.isOpen()) {
				setErrorMessage(IDEWorkbenchMessages.SaveAsDialog_closedProjectMessage);
				return false;
			}
		}
		IPath relativePath = new Path(resourceGroup.getResource());
		if (relativePath.segmentCount() == 0 || relativePath.hasTrailingSeparator()) {
			IStatus result = workspace.validateName("", IResource.FILE); //$NON-NLS-1$
			setErrorMessage(result.getMessage());
			return false;
		}
		IStatus result = workspace.validatePath(fullPath.toString(), IResource.FILE);
