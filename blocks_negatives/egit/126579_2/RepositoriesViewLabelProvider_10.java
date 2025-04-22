	protected StyledString getStyledTextForSubmodule(RepositoryTreeNode node) {
		Repository repository = (Repository) node.getObject();
		if (repository == null) {
			return new StyledString();
		}
		StyledString string = GitLabels.getChangedPrefix(repository);

		String path = Repository.stripWorkDir(node.getParent().getRepository()
				.getWorkTree(), repository.getWorkTree());
		string.append(path);

		Ref head;
		try {
			head = repository.exactRef(Constants.HEAD);
		} catch (IOException e) {
			return string;
		}
		if (head != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			if (head.isSymbolic())
				string.append(
						Repository.shortenRefName(head.getLeaf().getName()),
						StyledString.DECORATIONS_STYLER);
			else if (head.getObjectId() != null)
				string.append(abbreviate(head.getObjectId()),
						StyledString.DECORATIONS_STYLER);
			string.append(']', StyledString.DECORATIONS_STYLER);
			if (verboseBranchMode && head.getObjectId() != null) {
				RevCommit commit;
				try (RevWalk walk = new RevWalk(repository)) {
					commit = walk.parseCommit(head.getObjectId());
					string.append(' ');
					string.append(commit.getShortMessage(),
							StyledString.QUALIFIER_STYLER);
				} catch (IOException ignored) {
				}
			}
		}
		return string;
