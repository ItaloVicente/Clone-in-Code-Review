	private void writeHeadReflog() throws TeamException {
		try {
			RefLogWriter.writeReflog(repository, oldCommit.getCommitId(),
					newCommit.getCommitId(), "checkout: moving to " + refName,
					Constants.HEAD);
		} catch (IOException e) {
			throw new TeamException("Writing HEAD's reflog", e);
		}
	}

