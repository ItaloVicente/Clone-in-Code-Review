		if (o instanceof IResource) {
			RepositoryMapping mapping = RepositoryMapping
					.getMapping((IResource) o);
			if (mapping != null) {
				Repository repo = mapping.getRepository();
				input = new HistoryPageInput(repo,
						new IResource[] { (IResource) o });
			}
		} else if (o instanceof RepositoryTreeNode) {
			RepositoryTreeNode repoNode = (RepositoryTreeNode) o;
			switch (repoNode.getType()) {
			case FILE:
				File file = ((FileNode) repoNode).getObject();
				input = new HistoryPageInput(repoNode.getRepository(),
						new File[] { file });
				break;
			case FOLDER:
				File folder = ((FolderNode) repoNode).getObject();
				input = new HistoryPageInput(repoNode.getRepository(),
						new File[] { folder });
				break;
			default:
				input = new HistoryPageInput(repoNode.getRepository());
			}

		} else if (o instanceof HistoryPageInput)
			input = (HistoryPageInput) o;
		else if (o instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) o)
					.getAdapter(IResource.class);
			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping((IResource) o);
				Repository repo = mapping.getRepository();
				input = new HistoryPageInput(repo, new IResource[] { resource });
			}
		}
		if (input == null) {
			this.name = ""; //$NON-NLS-1$
			setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
