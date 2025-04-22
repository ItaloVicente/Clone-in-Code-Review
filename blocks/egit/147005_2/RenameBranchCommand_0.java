		Repository repository = node.getRepository();
		Ref branch = null;
		if (node instanceof RefNode) {
			branch = (Ref) node.getObject();
		} else if (node instanceof RepositoryNode) {
			try {
				branch = repository
						.exactRef(Constants.R_HEADS + repository.getBranch());
			} catch (IOException e) {
				Activator.logError("Cannot rename branch", e); //$NON-NLS-1$
			}
		}

		if (branch != null) {
			Shell shell = getShell(event);
			new BranchRenameDialog(shell, repository, branch).open();
		}
