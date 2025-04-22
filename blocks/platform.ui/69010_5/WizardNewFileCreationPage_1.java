		IPath path = new Path(getFileName());
		if (path.segmentCount() == 0 || path.hasTrailingSeparator()) {
			IStatus result = workspace.validateName("", IResource.FILE); //$NON-NLS-1$
			setErrorMessage(result.getMessage());
			return false;
		}
		IPath resourcePath = getContainerFullPath().append(getFileName());
		IStatus result = workspace.validatePath(resourcePath.toString(), IResource.FILE);
