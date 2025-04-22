	private interface FileSystemItem {
		boolean hasContainerAnyFiles();

		boolean isContainer();

		boolean exists();

		IPath getAbsolutePath();
	}

	private class FileItem implements FileSystemItem {

		@NonNull
		private final File file;

		public FileItem(@NonNull File file) {
			this.file = file;
		}

		@Override
		public IPath getAbsolutePath() {
			return new org.eclipse.core.runtime.Path(file.getAbsolutePath());
		}

		@Override
		public boolean isContainer() {
			return file.isDirectory();
		}

		@Override
		public boolean exists() {
			return file.exists();
		}

		@Override
		public boolean hasContainerAnyFiles() {
			if (!isContainer()) {
				throw new IllegalArgumentException("Container expected"); //$NON-NLS-1$
			}
