		public FileNode(IPath path, IFile file, Type type,
				IFileRevision leftRevision, IFileRevision rightRevision) {
			super(path);
			this.file = file;
			this.type = type;
			this.leftRevision = leftRevision;
			this.rightRevision = rightRevision;
		}
