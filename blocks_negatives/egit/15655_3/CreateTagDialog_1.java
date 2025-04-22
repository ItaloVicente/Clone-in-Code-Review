		} catch (IOException e) {
			Activator.logError(UIText.TagAction_errorWhileGettingRevCommits, e);
			setErrorMessage(UIText.TagAction_errorWhileGettingRevCommits);
		}
		RevCommit commit;
		long count = 0;
		try {
