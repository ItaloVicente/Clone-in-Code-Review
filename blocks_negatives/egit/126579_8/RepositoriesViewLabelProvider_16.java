			ObjectId refId = ref.getObjectId();
			refName.append(' ');
			RevCommit commit = getLatestCommit(node);
			if (commit != null) {
				refName.append(abbreviate(commit),
						StyledString.QUALIFIER_STYLER)
						.append(' ')
						.append(commit.getShortMessage(),
