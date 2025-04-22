
	protected void enableWorkingDirCommand(Object evaluationContext) {
		if (!(evaluationContext instanceof EvaluationContext)) {
			setBaseEnabled(false);
			return;
		}
		EvaluationContext context = (EvaluationContext) evaluationContext;
		Object selection = context
				.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (!(selection instanceof TreeSelection)) {
			setBaseEnabled(false);
			return;
		}
		Repository repository = null;
		TreeSelection treeSelection = (TreeSelection) selection;
		for (Iterator iterator = treeSelection.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (!(object instanceof RepositoryTreeNode)) {
				setBaseEnabled(false);
				return;
			}
			Repository nodeRepository = ((RepositoryTreeNode) object)
					.getRepository();
			if (repository == null)
				repository = nodeRepository;
			else if (repository != nodeRepository) {
				setBaseEnabled(false);
				return;
			}
			if (!(object instanceof WorkingDirNode)) {
				String path;
				if (object instanceof FolderNode) {
					path = ((FolderNode) object).getObject().getAbsolutePath();
				} else {
					if (object instanceof FileNode) {
						path = ((FileNode) object).getObject()
								.getAbsolutePath();
					} else {
						setBaseEnabled(false);
						return;
					}
				}
				if (path.startsWith(repository.getDirectory().getAbsolutePath())) {
					setBaseEnabled(false);
					return;
				}
			}
		}

		setBaseEnabled(true);
	}

