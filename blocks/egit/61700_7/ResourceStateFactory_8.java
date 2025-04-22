	private static class ResourceItem implements FileSystemItem {

		@NonNull
		private final IResource resource;

		public ResourceItem(@NonNull IResource resource) {
			this.resource = resource;
		}

		@Override
		public IPath getAbsolutePath() {
			return resource.getLocation();
		}

		@Override
		public boolean isContainer() {
			return isContainer(resource);
		}

		@Override
		public boolean hasContainerAnyFiles() {
			return containsFiles(resource);
		}

		private boolean isContainer(IResource rsc) {
			int type = rsc.getType();
			return type == IResource.FOLDER || type == IResource.PROJECT
					|| type == IResource.ROOT;
		}

		private boolean containsFiles(IResource rsc) {
			if (rsc instanceof IContainer) {
				IContainer container = (IContainer) rsc;
				try {
					return anyFile(container.members());
				} catch (CoreException e) {
