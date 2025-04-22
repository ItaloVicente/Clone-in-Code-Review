	protected StyledString getStyledTextForSubmodule(RepositoryTreeNode node) {
		StyledString string = new StyledString();
		Repository repository = (Repository) node.getObject();
		String path = Repository.stripWorkDir(node.getParent().getRepository()
				.getWorkTree(), repository.getWorkTree());
		string.append(path);

		RevCommit head = null;
		RevWalk walk = new RevWalk(repository);
		try {
			ObjectId headId = repository.resolve(Constants.HEAD);
			if (headId != null)
				head = walk.parseCommit(headId);
		} catch (IOException e) {
			return string;
		}
		if (head != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			string.append(head.abbreviate(7).name(),
					StyledString.DECORATIONS_STYLER);
			string.append(']', StyledString.DECORATIONS_STYLER);
			string.append(' ');
			string.append(head.getShortMessage(), StyledString.QUALIFIER_STYLER);
		}
		return string;
	}

