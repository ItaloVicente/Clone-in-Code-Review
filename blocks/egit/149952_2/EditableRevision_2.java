	private static class FakeResourceStorageEditorInput
			implements IStorageEditorInput {



		private final EditableRevision editable;

		private boolean adapt;

		public FakeResourceStorageEditorInput(EditableRevision revision) {
			editable = revision;
			adapt = true;
		}

		public void setAdapt(boolean adapt) {
			this.adapt = adapt;
		}

		@Override
		public boolean exists() {
			return true;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public String getName() {
			return editable.getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			return null;
		}

		@Override
		public String getToolTipText() {
			return editable.getName();
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			if (adapt) {
				return editable.adaptEditorInput(this, adapter);
			}
			return null;
		}

		private IStorage storage;

		@Override
		public IStorage getStorage() throws CoreException {
			if (storage == null) {
				storage = new IEncodedStorage() {

					@Override
					public <T> T getAdapter(Class<T> adapter) {
						return null;
					}

					@Override
					public boolean isReadOnly() {
						return false;
					}

					@Override
					public String getName() {
						return editable.getName();
					}

					@Override
					public IPath getFullPath() {
						return new Path(editable.getPath());
					}

					@Override
					public InputStream getContents() throws CoreException {
						return editable.getContents();
					}

					@Override
					public String getCharset() throws CoreException {
						return editable.getCharset();
					}
				};
			}
			return storage;
		}
	}
