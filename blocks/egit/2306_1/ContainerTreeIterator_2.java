
	private static final IResourceChangeListener rcl = new RCL();

	private static class RCL implements IResourceChangeListener {

		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta[] deltas = event.getDelta().getAffectedChildren();
			for (IResourceDelta delta : deltas) {
				try {
					IResource resource = delta.getResource();
					if (resource.getType() == IResource.FILE)
						resource.setSessionProperty(LENGTH_RESOURCE_KEY, null);
				} catch (CoreException e) {
					exceptions.handleException(e);
				}
			}
		}
	}

	private static void registerRCL() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(rcl,
				IResourceChangeEvent.POST_CHANGE);
	}
