		PersonIdent author = commit.getAuthorIdent();
		PersonIdent committer = commit.getCommitterIdent();
		if (author != null && committer != null) {
			if (author.getName().equals(committer.getName())) {
				styled.append(MessageFormat.format(
						UIText.RepositoryCommit_AuthorDate, author.getName(),
						formatDate(author.getWhen())),
						StyledString.QUALIFIER_STYLER);
			} else {
				styled.append(MessageFormat.format(
						UIText.RepositoryCommit_AuthorDateCommitter,
						author.getName(), formatDate(author.getWhen()),
						committer.getName()), StyledString.QUALIFIER_STYLER);
			}
		}
