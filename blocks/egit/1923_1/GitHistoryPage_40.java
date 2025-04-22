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
