			return super.hashCode();
		}

		@Override
		public Repository getRepository() {
			return repository;
		}

		@Override
		public String getGitPath() {
			return gitPath;
		}

		@Override
		public Source getSource() {
			return Source.WORKING_TREE;
		}

		@Override
		public AnyObjectId getCommitId() {
			return null;
		}
	}

	private static class FolderNode extends DiffNode {

		private final Image image;

		private String name;

		private IContainer container;

		private IPath path;

		FolderNode(IDiffContainer parent, String name, Image image) {
			super(parent, Differencer.NO_CHANGE);
			this.name = name;
			this.image = image;
		}

		@Override
		public String getType() {
			return ITypedElement.FOLDER_TYPE;
		}

		@Override
		public String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}

		@Override
		public Image getImage() {
			return image;
		}

		void setContainer(IContainer container) {
			this.container = container;
		}

		IContainer getContainer() {
			return container;
		}

		void setPath(IPath path) {
			this.path = path;
		}

		IPath getPath() {
			return path;
		}

		@Override
		public boolean equals(Object other) {
			return super.equals(other);
		}

		@Override
		public int hashCode() {
