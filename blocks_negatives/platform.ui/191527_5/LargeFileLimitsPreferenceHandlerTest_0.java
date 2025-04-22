
	private static class TestEditorInput implements IPathEditorInput {

		private final Path temporaryFile;

		TestEditorInput() throws IOException {
			temporaryFile = Files.createTempFile("test_file", "." + TXT_EXTENSION);
			Files.write(temporaryFile, Arrays.asList("some line 1", "some line 2"));
		}

		void dispose() throws IOException {
			Files.delete(temporaryFile);
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
			return "test editor input";
		}

		@Override
		public IPersistableElement getPersistable() {
			return null;
		}

		@Override
		public String getToolTipText() {
			return getName();
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			return null;
		}

		@Override
		public IPath getPath() {
			return new org.eclipse.core.runtime.Path(temporaryFile.toString());
		}

	}
