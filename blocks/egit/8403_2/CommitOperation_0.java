		if (enteredAuthor == null)
			throw new TeamException(NLS.bind(
					CoreText.CommitOperation_errorParsingPersonIdent, author));
		if (enteredCommitter == null)
			throw new TeamException(
					NLS.bind(CoreText.CommitOperation_errorParsingPersonIdent,
							committer));
