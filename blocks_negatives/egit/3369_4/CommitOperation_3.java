		if (amending && filesToCommit.length == 0) {
			commit(repos[0], new ArrayList<String>(), commitDate, timeZone, authorIdent, committerIdent);
			return;
		}
		for (java.util.Map.Entry<Repository, List<String>> entry : filesByRepo.entrySet()) {
			List<String> commitFileList = entry.getValue();
			Repository repo = entry.getKey();
			commit(repo, commitFileList, commitDate, timeZone, authorIdent, committerIdent);
		}
	}

	private void commit(Repository repo, List<String> commitFileList, final Date commitDate,
			final TimeZone timeZone, final PersonIdent authorIdent,
			final PersonIdent committerIdent) throws TeamException {
