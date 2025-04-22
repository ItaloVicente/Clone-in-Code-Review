
	private String getSimpleText(RepositoryTreeNode node) {
		switch (node.getType()) {
		case REPO:
			Repository repository = (Repository) node.getObject();
			return GitLabels.getPlainShortLabel(repository);
		case FILE:
		case FOLDER:
			return ((File) node.getObject()).getName();
		case BRANCHES:
			return UIText.RepositoriesView_Branches_Nodetext;
		case LOCAL:
			return UIText.RepositoriesViewLabelProvider_LocalNodetext;
		case REMOTETRACKING:
			return UIText.RepositoriesViewLabelProvider_RemoteTrackingNodetext;
		case BRANCHHIERARCHY:
			IPath fullPath = (IPath) node.getObject();
			return fullPath.lastSegment();
		case TAGS:
			return UIText.RepositoriesViewLabelProvider_TagsNodeText;
		case ADDITIONALREFS:
			return UIText.RepositoriesViewLabelProvider_SymbolicRefNodeText;
		case REMOTES:
			return UIText.RepositoriesView_RemotesNodeText;
		case SUBMODULES:
			return UIText.RepositoriesViewLabelProvider_SubmodulesNodeText;
		case STASH:
			return UIText.RepositoriesViewLabelProvider_StashNodeText;
		case STASHED_COMMIT:
			return MessageFormat.format(
					"{0}@'{'{1}'}'", //$NON-NLS-1$
					Constants.STASH,
					Integer.valueOf(((StashedCommitNode) node).getIndex()));
		case REF:
		case TAG: {
			Ref ref = (Ref) node.getObject();
			String refName = Repository.shortenRefName(ref.getName());
			if (node.getParent().getType() == RepositoryTreeNodeType.BRANCHHIERARCHY) {
				int index = refName.lastIndexOf('/');
				refName = refName.substring(index + 1);
			}
			return refName;
		}
		case ADDITIONALREF: {
			Ref ref = (Ref) node.getObject();
			String refName = Repository.shortenRefName(ref.getName());
			if (ref.isSymbolic()) {
				refName = refName
						+ ref.getLeaf().getName()
			} else {
						+ ObjectId.toString(ref.getObjectId());
			}
			return refName;
		}
		case WORKINGDIR:
					+ node.getRepository().getWorkTree().getAbsolutePath();
		case REMOTE:
		case PUSH:
		case FETCH:
		case ERROR:
			return (String) node.getObject();

		}
		return null;
	}

	/**
	 * @see org.eclipse.core.commands.IStateListener#handleStateChange(org.eclipse.core.commands.State,
	 *      java.lang.Object)
	 */
	@Override
	public void handleStateChange(State state, Object oldValue) {
		try {
			this.verboseBranchMode = ((Boolean) state.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

