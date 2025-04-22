		RevCommit commit;
		try {
			commit = commitCmd.call();
		} catch (JGitInternalException e) {
			throw die(e.getMessage());
		}
