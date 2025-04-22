	@SuppressWarnings("unchecked")
	private void showResource(final IResource resource) {
		try {
			IProject project = resource.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null)
				return;

			boolean added = repositoryUtil.addConfiguredRepository(mapping
					.getRepository().getDirectory());
			if (added) {
				scheduleRefresh();
			}

			boolean doSetSelection = false;

			if (this.scheduledJob != null) {
				int state = this.scheduledJob.getState();
				if (state == Job.WAITING || state == Job.RUNNING) {
					this.scheduledJob
							.addJobChangeListener(new JobChangeAdapter() {

								@Override
								public void done(IJobChangeEvent event) {
									showResource(resource);
								}
							});
				} else {
					doSetSelection = true;
				}
			}

			if (doSetSelection) {
				RepositoryTreeNode currentNode = null;
				ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
						.getContentProvider();
				for (Object repo : cp.getElements(getCommonViewer().getInput())) {
					RepositoryTreeNode node = (RepositoryTreeNode) repo;
					if (mapping.getRepository().getDirectory().equals(
							((Repository) node.getObject()).getDirectory())) {
						for (Object child : cp.getChildren(node)) {
							RepositoryTreeNode childNode = (RepositoryTreeNode) child;
							if (childNode.getType() == RepositoryTreeNodeType.WORKINGDIR) {
								currentNode = childNode;
								break;
							}
						}
						break;
					}
				}

				IPath relPath = new Path(mapping.getRepoRelativePath(resource));

				for (String segment : relPath.segments()) {
					for (Object child : cp.getChildren(currentNode)) {
						RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
						if (childNode.getObject().getName().equals(segment)) {
							currentNode = childNode;
							break;
						}
					}
				}

				final RepositoryTreeNode selNode = currentNode;
