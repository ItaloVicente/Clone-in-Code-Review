	private void setAuthorAndCommitter(CommitCommand commitCommand) {
		final Date commitDate = new Date();
		final TimeZone timeZone = TimeZone.getDefault();

		final PersonIdent enteredAuthor = RawParseUtils.parsePersonIdent(author);
		final PersonIdent enteredCommitter = RawParseUtils.parsePersonIdent(committer);

		PersonIdent authorIdent = new PersonIdent(enteredAuthor, commitDate, timeZone);
		final PersonIdent committerIdent = new PersonIdent(enteredCommitter, commitDate, timeZone);

		if (amending) {
			RepositoryUtil repoUtil = Activator.getDefault().getRepositoryUtil();
			RevCommit headCommit = repoUtil.parseHeadCommit(repo);
			if (headCommit != null) {
				final PersonIdent headAuthor = headCommit.getAuthorIdent();
				authorIdent = new PersonIdent(enteredAuthor,
						headAuthor.getWhen(), headAuthor.getTimeZone());
			}
		}

		commitCommand.setAuthor(authorIdent);
		commitCommand.setCommitter(committerIdent);
	}
