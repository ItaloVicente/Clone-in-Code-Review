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

				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						selectReveal(new StructuredSelection(selNode));
					}
				});

			}
		} catch (RuntimeException rte) {
			Activator.handleError(rte.getMessage(), rte, false);
		}
	}

	public void refresh() {
		lastInputUpdate = -1l;
		scheduleRefresh();
	}

	private void scheduleRefresh() {
		if (scheduledJob != null && scheduledJob.getState() == Job.RUNNING) {
