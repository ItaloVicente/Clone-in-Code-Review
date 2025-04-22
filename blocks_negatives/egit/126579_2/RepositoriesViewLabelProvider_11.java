	protected StyledString getStyledTextForCommit(StashedCommitNode node) {
		StyledString string = new StyledString();
		RevCommit commit = node.getObject();
		string.append(MessageFormat.format("{0}@'{'{1}'}'", //$NON-NLS-1$
				Constants.STASH, Integer.valueOf(node.getIndex())));
		string.append(' ');
		string.append('[', StyledString.DECORATIONS_STYLER);
		string.append(abbreviate(commit), StyledString.DECORATIONS_STYLER);
		string.append(']', StyledString.DECORATIONS_STYLER);
		string.append(' ');
		string.append(commit.getShortMessage(), StyledString.QUALIFIER_STYLER);
		return string;
	}

	/**
	 * Gets the {@link StyledString} for a {@link SubmodulesNode}.
	 *
	 * @param node
	 *            to get the text for
	 * @return the {@link StyledString}
	 */
	protected StyledString getStyledTextForSubmodules(SubmodulesNode node) {
		String label = getSimpleText(node);
		if (label == null) {
			return new StyledString();
		}
		StyledString styled = new StyledString(label);
		Repository repository = node.getRepository();
		if (repository != null) {
			boolean hasChanges = false;
			try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
				while (!hasChanges && walk.next()) {
					Repository submodule = walk.getRepository();
					if (submodule != null) {
						Repository cached = org.eclipse.egit.core.Activator
								.getDefault().getRepositoryCache()
								.lookupRepository(submodule.getDirectory()
										.getAbsoluteFile());
						hasChanges = cached != null
								&& RepositoryUtil.hasChanges(cached);
						submodule.close();
					}
				}
			} catch (IOException e) {
				hasChanges = false;
			}
			if (hasChanges) {
				StyledString prefixed = new StyledString();
				prefixed.append('>', StyledString.DECORATIONS_STYLER);
				prefixed.append(' ').append(styled);
				return prefixed;
			}
		}
		return styled;
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (!(element instanceof RepositoryTreeNode))
			return null;

		RepositoryTreeNode node = (RepositoryTreeNode) element;

		switch (node.getType()) {
		case REPO:
			if (node.getParent() != null
					&& node.getParent().getType() == RepositoryTreeNodeType.SUBMODULES)
				return getStyledTextForSubmodule(node);
			return GitLabels.getStyledLabelExtendedSafe(node.getObject());
		case ADDITIONALREF:
			Ref ref = (Ref) node.getObject();
			StyledString refName = new StyledString(
					Repository.shortenRefName(ref.getName()));

			if (ref.isSymbolic()) {
				refName.append(' ');
				refName.append('[', StyledString.DECORATIONS_STYLER);
				refName.append(ref.getLeaf().getName(),
						StyledString.DECORATIONS_STYLER);
				refName.append(']', StyledString.DECORATIONS_STYLER);
			}
			ObjectId refId = ref.getObjectId();
			refName.append(' ');
			RevCommit commit = getLatestCommit(node);
			if (commit != null) {
				refName.append(abbreviate(commit),
						StyledString.QUALIFIER_STYLER)
						.append(' ')
						.append(commit.getShortMessage(),
								StyledString.QUALIFIER_STYLER);
			} else if (!ref.isSymbolic() || refId != null) {
				refName.append(abbreviate(refId),
						StyledString.QUALIFIER_STYLER);
			} else {
				refName.append(
						UIText.RepositoriesViewLabelProvider_UnbornBranchText,
						StyledString.QUALIFIER_STYLER);
			}
			return refName;
		case WORKINGDIR:
			StyledString dirString = new StyledString(
					UIText.RepositoriesView_WorkingDir_treenode);
			dirString.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			dirString.append(node.getRepository().getWorkTree()
					.getAbsolutePath(), StyledString.QUALIFIER_STYLER);
			return dirString;

		case REF:
			StyledString styled = null;
			String nodeText = getSimpleText(node);
			if (nodeText != null) {
				styled = new StyledString(nodeText);
				if (verboseBranchMode) {
					RevCommit latest = getLatestCommit(node);
					if (latest != null)
						styled.append(' ')
								.append(abbreviate(latest),
										StyledString.QUALIFIER_STYLER)
								.append(' ')
								.append(latest.getShortMessage(),
										StyledString.QUALIFIER_STYLER);
				}
			}
			return styled;
		case TAG:
			return getStyledTextForTag((TagNode) node);
		case STASHED_COMMIT:
			return getStyledTextForCommit((StashedCommitNode) node);
		case SUBMODULES:
			return getStyledTextForSubmodules((SubmodulesNode) node);
		case PUSH:
		case FETCH:
		case FILE:
		case FOLDER:
		case BRANCHES:
		case LOCAL:
		case REMOTETRACKING:
		case BRANCHHIERARCHY:
		case TAGS:
		case ADDITIONALREFS:
		case REMOTES:
		case REMOTE:
		case STASH:
		case ERROR: {
			String label = getSimpleText(node);
			if (label != null)
				return new StyledString(label);
		}

		}

		return null;

	}

	private StyledString getStyledTextForTag(TagNode node) {
		String tagText = getSimpleText(node);
		if (tagText != null) {
			StyledString styled = new StyledString(tagText);
			if (verboseBranchMode) {
				if (node.getCommitId() != null
						&& node.getCommitId().length() > 0)
					styled.append(' ')
							.append(node.getCommitId().substring(0, 7),
									StyledString.QUALIFIER_STYLER)
							.append(' ')
							.append(node.getCommitShortMessage(),
									StyledString.QUALIFIER_STYLER);
			}
			return styled;
		} else {
			return null;
		}
