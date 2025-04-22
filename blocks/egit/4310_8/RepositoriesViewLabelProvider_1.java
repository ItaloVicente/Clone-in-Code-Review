	protected StyledString getStyledTextForSubmodule(RepositoryTreeNode node) {
		StyledString string = new StyledString();
		Repository repository = (Repository) node.getObject();
		String path = Repository.stripWorkDir(node.getParent().getRepository()
				.getWorkTree(), repository.getWorkTree());
		string.append(path);

		Ref head;
		try {
			head = repository.getRef(Constants.HEAD);
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
			else
				string.append(head.getObjectId().abbreviate(7).name(),
						StyledString.DECORATIONS_STYLER);
			string.append(']', StyledString.DECORATIONS_STYLER);
			if (verboseBranchMode) {
				RevWalk walk = new RevWalk(repository);
				RevCommit commit;
				try {
					commit = walk.parseCommit(head.getObjectId());
					string.append(' ');
					string.append(commit.getShortMessage(),
							StyledString.QUALIFIER_STYLER);
				} catch (IOException ignored) {
				}
			}
		}
		return string;
	}

