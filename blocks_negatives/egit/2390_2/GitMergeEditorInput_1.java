		IResourceVisitor fileCollector = new IResourceVisitor() {
			public boolean visit(IResource resource) throws CoreException {
				if (Team.isIgnoredHint(resource))
					return false;
				if (resource.getType() == IResource.FILE) {
					files.add((IFile) resource);
				}
				return true;
