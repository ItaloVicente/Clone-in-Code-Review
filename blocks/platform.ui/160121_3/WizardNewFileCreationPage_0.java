
		IStatus result = null;
		String separator = java.io.File.separator;
		if (!resourceName.contains(separator)) {// just a file (no subfolder(s))
			result = workspace.validateName(resourceName, IResource.FILE);
		} else {
			result = workspace.validatePath(resourceGroup.getContainerFullPath().toString() + separator + resourceName,
					IResource.FILE);
		}

