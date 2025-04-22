		if (branch == null)
			return null;
		string.append("[", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
		string.append(branch, StyledString.DECORATIONS_STYLER);

		RepositoryState repositoryState = repository.getRepositoryState();
		if (repositoryState != RepositoryState.SAFE) {
			string.append(" - ", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
			string.append(repositoryState.getDescription(), StyledString.DECORATIONS_STYLER);
