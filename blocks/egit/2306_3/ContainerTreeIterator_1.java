
	private static final IResourceChangeListener rcl = new RCL();

	private static class RCL implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {

					public boolean visit(IResourceDelta delta)
							throws CoreException {
						final IResource resource = delta.getResource();
						if (resource != null
								&& resource.getType() == IResource.FILE)
							try {
								resource.setSessionProperty(FILE_LENGTH_KEY,
										null);
							} catch (CoreException e) {
							}
						return true;
					}
				}, true);
			} catch (CoreException e) {
			}
		}
	}

	private static void registerRCL() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(rcl,
				IResourceChangeEvent.POST_CHANGE);
	}
