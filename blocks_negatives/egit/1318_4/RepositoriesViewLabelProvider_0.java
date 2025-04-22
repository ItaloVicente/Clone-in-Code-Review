		switch (node.getType()) {
		case REPO:
			File directory = ((Repository) node.getObject()).getDirectory();
			StringBuilder sb = new StringBuilder();
			sb.append(directory.getParentFile().getName());
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
						+ ref.getLeaf().getName();
			}
			return refName;
		case WORKINGDIR:
			if (node.getRepository().getConfig().getBoolean(
					"core", "bare", false)) //$NON-NLS-1$ //$NON-NLS-2$
				return UIText.RepositoriesView_WorkingDir_treenode
						+ UIText.RepositoriesViewLabelProvider_BareRepositoryMessage;
			else
						+ node.getRepository().getWorkTree().getAbsolutePath();
		case PUSH:
		case FETCH:
			return (String) node.getObject();

		}
