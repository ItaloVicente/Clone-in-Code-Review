		public FileNode(IPath path, Type type, IFileRevision leftRevision, IFileRevision rightRevision) {
			super(path);
			this.type = type;
			this.leftRevision = leftRevision;
			this.rightRevision = rightRevision;
		}
