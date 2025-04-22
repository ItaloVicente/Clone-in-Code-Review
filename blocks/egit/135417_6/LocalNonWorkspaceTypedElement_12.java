	private static class FakeResourceFileStoreEditorInput
			extends FileStoreEditorInput {

		private IResource resource;

		public FakeResourceFileStoreEditorInput(IFileStore store, IResource resource) {
			super(store);
			this.resource = resource;
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			if (adapter == IFile.class || adapter == IResource.class) {
				if (resource != null && adapter.isInstance(resource)) {
					return adapter.cast(resource);
				}
			}
			return super.getAdapter(adapter);
		}

		public void setResource(IResource resource) {
			this.resource = resource;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			return super.equals(o);
		}
	}
