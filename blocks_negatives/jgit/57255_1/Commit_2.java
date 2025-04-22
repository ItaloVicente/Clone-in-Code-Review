		CommitCommand commitCmd = new Git(db).commit();
		if (author != null)
			commitCmd.setAuthor(RawParseUtils.parsePersonIdent(author));
		if (message != null)
			commitCmd.setMessage(message);
		if (only && paths.isEmpty())
			throw die(CLIText.get().pathsRequired);
		if (only && all)
			throw die(CLIText.get().onlyOneOfIncludeOnlyAllInteractiveCanBeUsed);
		if (!paths.isEmpty())
			for (String p : paths)
				commitCmd.setOnly(p);
		commitCmd.setAmend(amend);
		commitCmd.setAll(all);
		Ref head = db.getRef(Constants.HEAD);
		RevCommit commit;
		try {
			commit = commitCmd.call();
		} catch (JGitInternalException e) {
			throw die(e.getMessage());
		}
