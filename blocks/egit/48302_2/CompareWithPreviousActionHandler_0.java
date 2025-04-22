	private PreviousCommit getLatestRevision(ExecutionEvent event,
			IResource[] resources) throws IOException {
		PreviousCommit latestRevision = null;
		for (IResource resource : resources) {
			PreviousCommit previousRevision = getPreviousRevision(event,
					resource);
			if (latestRevision == null) {
				latestRevision = previousRevision;
			} else if (previousRevision != null) {
				final int latestTime = latestRevision.commit.getCommitTime();
				final int currentTime = previousRevision.commit.getCommitTime();
				if (currentTime > latestTime) {
					latestRevision = previousRevision;
				}
			}
		}
		return latestRevision;
	}

