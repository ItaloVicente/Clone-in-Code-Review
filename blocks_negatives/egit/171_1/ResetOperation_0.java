

	private void writeReflog(String reflogRelPath) throws IOException {
		String name = refName;
		if (name.startsWith("refs/heads/"))
			name = name.substring(11);
		if (name.startsWith("refs/remotes/"))
			name = name.substring(13);

		String message = "reset --" + type.toString().toLowerCase() + " " + name;

		RefLogWriter.writeReflog(repository, previousCommit.getCommitId(), commit.getCommitId(), message, reflogRelPath);
	}

	private void writeReflogs() throws TeamException {
		try {
			writeReflog(Constants.HEAD);
			writeReflog(repository.getFullBranch());
		} catch (IOException e) {
			throw new TeamException("Writing reflogs", e);
		}
	}
