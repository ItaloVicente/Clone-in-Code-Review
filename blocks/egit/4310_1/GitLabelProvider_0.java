	protected StyledString getStyledTextForSubmodule(RepositoryTreeNode node)
			throws IOException {
		StyledString string = new StyledString();
		Repository repository = (Repository) node.getObject();
		String path = Repository.stripWorkDir(node.getParent().getRepository()
				.getWorkTree(), repository.getWorkTree());
		string.append(path);

		String branch = repository.getBranch();
		if (branch != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			string.append(branch, StyledString.DECORATIONS_STYLER);

			RepositoryState repositoryState = repository.getRepositoryState();
			if (repositoryState != RepositoryState.SAFE) {
				string.append(" - ", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
				string.append(repositoryState.getDescription(),
						StyledString.DECORATIONS_STYLER);
			}
			string.append(']', StyledString.DECORATIONS_STYLER);
		}
		return string;
	}

