			IPath containerPath = resourceGroup.getContainerFullPath();
			if (containerPath == null) {
				result = workspace.validatePath(resourceName, IResource.FILE);
			} else {
				result = workspace.validatePath(containerPath.toString() + IPath.SEPARATOR + resourceName,
						IResource.FILE);
			}
