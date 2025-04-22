			@Override
			public void setValue(String string) {
				resourceGroup.setResource(string);
			}

			@Override
			public IResource getResource() {
				IPath path = resourceGroup.getContainerFullPath();
				if (path != null) {
					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					IResource resource = root.findMember(path);
					if (resource != null && resource instanceof IContainer) {
						String resourceName = resourceGroup.getResource();
						if (resourceName.length() > 0) {
							try {
								return ((IContainer) resource).getFolder(Path.fromOSString(resourceName));
							} catch (IllegalArgumentException e) {
