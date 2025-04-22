			@Override
			public IResource getResource() {
				IPath path = resourceGroup.getContainerFullPath();
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource resource = root.findMember(path);
				if (resource != null && resource instanceof IContainer) {
					String resourceName = resourceGroup.getResource();
					if (resourceName.length() > 0) {
						try {
							return ((IContainer) resource).getFile(Path.fromOSString(resourceName));
						} catch (IllegalArgumentException e) {
