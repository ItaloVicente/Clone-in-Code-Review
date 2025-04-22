
	private IDiffContainer getFileParent(IDiffContainer root,
			IPath repositoryPath, IFile file, IPath location) {
		int projectSegment = -1;
		String projectName = null;
		if (file != null) {
			IProject project = file.getProject();
			IPath projectLocation = project.getLocation();
			if (projectLocation != null) {
				IPath projectPath = project.getLocation().makeRelativeTo(
						repositoryPath);
				projectSegment = projectPath.segmentCount() - 1;
				projectName = project.getName();
			}
		}

		IPath path = location.makeRelativeTo(repositoryPath);
		IDiffContainer child = root;
		for (int i = 0; i < path.segmentCount() - 1; i++) {
			if (i == projectSegment)
				child = getOrCreateChild(child, projectName, true);
			else
				child = getOrCreateChild(child, path.segment(i), false);
		}
		return child;
	}

	private DiffNode getOrCreateChild(IDiffContainer parent, final String name,
			final boolean projectMode) {
		for (IDiffElement child : parent.getChildren()) {
			if (child.getName().equals(name)) {
				return ((DiffNode) child);
			}
		}
		DiffNode child = new DiffNode(parent, Differencer.NO_CHANGE) {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public Image getImage() {
				if (projectMode)
					return PROJECT_IMAGE;
				else
					return FOLDER_IMAGE;
			}
		};
		return child;
	}

	@Override
	public boolean canRunAsJob() {
		return true;
	}

	private static class LocalResourceSaver
			implements ISharedDocumentAdapterListener {

		LocalResourceTypedElement element;

		public LocalResourceSaver(LocalResourceTypedElement element) {
			this.element = element;
		}

		protected void save() throws CoreException {
			element.saveDocument(true, null);
			refreshIndexDiff();
		}

		private void refreshIndexDiff() {
			IResource resource = element.getResource();
			if (resource != null && HiddenResources.INSTANCE
					.isHiddenProject(resource.getProject())) {
				String gitPath = null;
				Repository repository = null;
				URI uri = resource.getLocationURI();
				if (EFS.SCHEME_FILE.equals(uri.getScheme())) {
					IPath location = new Path(uri.getSchemeSpecificPart());
					repository = ResourceUtil.getRepository(location);
					if (repository != null) {
						location = ResourceUtil.getRepositoryRelativePath(
								location, repository);
						if (location != null) {
							gitPath = location.toPortableString();
						}
					}
				} else {
					repository = HiddenResources.INSTANCE.getRepository(uri);
					if (repository != null) {
						gitPath = HiddenResources.INSTANCE.getGitPath(uri);
					}
				}
				if (gitPath != null && repository != null) {
					IndexDiffCacheEntry indexDiffCacheForRepository = IndexDiffCache
							.getInstance().getIndexDiffCacheEntry(repository);
					if (indexDiffCacheForRepository != null) {
						indexDiffCacheForRepository.refreshFiles(
								Collections.singletonList(gitPath));
					}
				}
			}
		}

		@Override
		public void handleDocumentConnected() {
		}

		@Override
		public void handleDocumentDisconnected() {
		}

		@Override
		public void handleDocumentFlushed() {
			try {
				save();
			} catch (CoreException e) {
				Activator.handleStatus(e.getStatus(), true);
			}
		}

		@Override
		public void handleDocumentDeleted() {
		}

		@Override
		public void handleDocumentSaved() {
		}
	}

	private static class HiddenResourceTypedElement
			extends LocalResourceTypedElement {

		private final IFile realFile;

		public HiddenResourceTypedElement(IFile file, IFile realFile) {
			super(file);
			this.realFile = realFile;
		}

		public IFile getRealFile() {
			return realFile;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
