
	private void refresh(RemoteResourceMappingContext context, IFile... file)
			throws CoreException {
		context.refresh(new ResourceTraversal[] { new ResourceTraversal(file,
				1, 0) }, 0, new NullProgressMonitor());
	}
