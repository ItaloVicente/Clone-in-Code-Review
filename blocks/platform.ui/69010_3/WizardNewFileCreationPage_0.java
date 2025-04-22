		if (resourceName == null || resourceName.isEmpty()) {
			IStatus result = workspace.validateName(resourceName, IResource.FILE);
			setErrorMessage(result.getMessage());
			return false;
		}
		IPath path = new Path(resourceName);
		if (path.isAbsolute() || path.isRoot() || path.hasTrailingSeparator() || resourceName.endsWith("/.") //$NON-NLS-1$
				|| resourceName.endsWith("/..")) { //$NON-NLS-1$
			setErrorMessage(NLS.bind(IDEWorkbenchMessages.ResourceGroup_invalidFilename, resourceName));
			return false;
		}
		IPath resourcePath = getContainerFullPath().append(getFileName());
		IStatus result = workspace.validatePath(resourcePath.toString(), IResource.FOLDER);
		if (!result.isOK()) {
			setErrorMessage(result.getMessage());
			return false;
		}
		result = workspace.validateName(resourcePath.lastSegment(), IResource.FILE);
