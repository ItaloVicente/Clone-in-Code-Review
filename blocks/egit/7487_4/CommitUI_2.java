		Job job = createCommitJob(repository, commitOperation, openNewCommit);
		job.schedule();
	}

	private static Job createCommitJob(final Repository repository,
			final CommitOperation commitOperation, final boolean openNewCommit) {
