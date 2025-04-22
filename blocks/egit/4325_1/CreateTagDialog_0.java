		RevCommit commit;
		long count = 0;
		try {
			while ((commit = revWalk.next()) != null
					&& count < MAX_COMMIT_COUNT) {
				commits.add(commit);
				count++;
			}
		} catch (IOException e) {
			Activator.logError(UIText.TagAction_errorWhileGettingRevCommits, e);
			setErrorMessage(UIText.TagAction_errorWhileGettingRevCommits);
		}
