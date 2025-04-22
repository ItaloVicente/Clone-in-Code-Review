	public StyledString getStyledText(Object element) {
		if (!(element instanceof RepositoryTreeNode))
			return null;

		RepositoryTreeNode node = (RepositoryTreeNode) element;

		String label = getSimpleText(node);
		if (label == null) {
			return new StyledString(element.toString());
		}
		StyledString text = new StyledString(label);

		try {
			switch (node.getType()) {
			case REPO:
				Repository repository = (Repository) node.getObject();
				File directory = repository.getDirectory();
				StyledString string = new StyledString(directory.getParentFile().getName());
				string.append(" - " + directory.getAbsolutePath(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
				string.append(" [" + repository.getBranch() + "]", StyledString.DECORATIONS_STYLER);  //$NON-NLS-1$//$NON-NLS-2$
				return string;
			case SYMBOLICREF:
				Ref ref = (Ref) node.getObject();
				StyledString refName = new StyledString(node.getRepository().shortenRefName(ref.getName()));
				if (ref.isSymbolic()) {
					refName.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
					refName.append(ref.getLeaf().getName(), StyledString.QUALIFIER_STYLER);
				}
				return refName;
			case WORKINGDIR:
				StyledString dirString = new StyledString(UIText.RepositoriesView_WorkingDir_treenode);
				dirString.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
				if (node.getRepository().getConfig().getBoolean(
						"core", "bare", false)) { //$NON-NLS-1$ //$NON-NLS-2$
					dirString.append(UIText.RepositoriesViewLabelProvider_BareRepositoryMessage, StyledString.QUALIFIER_STYLER);
				} else {
					dirString.append(node.getRepository().getWorkTree().getAbsolutePath(), StyledString.QUALIFIER_STYLER);
				}
				return dirString;
			case PUSH:
			case FETCH:
			case FILE:
			case FOLDER:
			case BRANCHES:
			case LOCALBRANCHES:
			case REMOTEBRANCHES:
			case TAGS:
			case SYMBOLICREFS:
			case REMOTES:
			case REMOTE:
			case ERROR:
			case REF:
			case TAG:

			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return text;

	}

	private String getSimpleText(RepositoryTreeNode node) {
		switch (node.getType()) {
		case REPO:
			File directory = ((Repository) node.getObject()).getDirectory();
			StringBuilder sb = new StringBuilder();
			sb.append(directory.getParentFile().getName());
			sb.append(" - "); //$NON-NLS-1$
			sb.append(directory.getAbsolutePath());
			return sb.toString();
		case FILE:
		case FOLDER:
			return ((File) node.getObject()).getName();
		case BRANCHES:
			return UIText.RepositoriesView_Branches_Nodetext;
		case LOCALBRANCHES:
			return UIText.RepositoriesViewLabelProvider_LocalBranchesNodetext;
		case REMOTEBRANCHES:
			return UIText.RepositoriesViewLabelProvider_RemoteBrancheNodetext;
		case TAGS:
			return UIText.RepositoriesViewLabelProvider_TagsNodeText;
		case SYMBOLICREFS:
			return UIText.RepositoriesViewLabelProvider_SymbolicRefNodeText;
		case REMOTES:
			return UIText.RepositoriesView_RemotesNodeText;
		case REMOTE:
		case ERROR:
			return (String) node.getObject();
		case REF:
		case TAG:
		case SYMBOLICREF:
			Ref ref = (Ref) node.getObject();
			String refName = node.getRepository().shortenRefName(ref.getName());
			if (ref.isSymbolic()) {
				refName = refName + " - " //$NON-NLS-1$
				+ ref.getLeaf().getName();
			}
			return refName;
		case WORKINGDIR:
			if (node.getRepository().getConfig().getBoolean(
					"core", "bare", false)) //$NON-NLS-1$ //$NON-NLS-2$
				return UIText.RepositoriesView_WorkingDir_treenode
				+ " - " //$NON-NLS-1$
				+ UIText.RepositoriesViewLabelProvider_BareRepositoryMessage;
			else
				return UIText.RepositoriesView_WorkingDir_treenode + " - " //$NON-NLS-1$
				+ node.getRepository().getWorkTree().getAbsolutePath();
		case PUSH:
		case FETCH:
			return (String) node.getObject();

		}
		return null;
	}

