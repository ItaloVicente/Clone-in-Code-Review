	private void prepareCommitObject(RevCommit commit) {
		if (commit != null && commit.getRawBuffer() == null)
			try {
				new RevWalk(getRepository()).parseBody(commit);
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
	}

