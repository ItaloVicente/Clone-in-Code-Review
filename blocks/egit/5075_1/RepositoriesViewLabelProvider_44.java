	protected StyledString getStyledTextForCommit(StashedCommitNode node) {
		StyledString string = new StyledString();
		RevCommit commit = node.getObject();
		string.append(MessageFormat.format("{0}@'{'{1}'}'", //$NON-NLS-1$
				Constants.STASH, Integer.valueOf(node.getIndex())));
		string.append(' ');
		string.append('[', StyledString.DECORATIONS_STYLER);
		string.append(commit.abbreviate(7).name(),
				StyledString.DECORATIONS_STYLER);
		string.append(']', StyledString.DECORATIONS_STYLER);
		string.append(' ');
		string.append(commit.getShortMessage(), StyledString.QUALIFIER_STYLER);
		return string;
	}

