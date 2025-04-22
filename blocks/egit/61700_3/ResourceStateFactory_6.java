	private class ResourceItem implements FileSystemItem {

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
			int type = resource.getType();
			return type == IResource.FOLDER || type == IResource.PROJECT
					|| type == IResource.ROOT;
		}

		@Override
		public boolean exists() {
			return resource.exists();
		}

		@Override
		public boolean hasContainerAnyFiles() {
			return hasContainerAnyFiles(resource);
		}

		private boolean hasContainerAnyFiles(IResource rsc) {
			if (rsc instanceof IContainer) {
				IContainer container = (IContainer) rsc;
				try {
					return anyFile(container.members());
				} catch (CoreException e) {
