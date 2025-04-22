		Job commitJob = createCommitJob(repo, commitOperation, false);
		if (commitDialog.isPushEnabled())
			pushWhenFinished(commitJob);

		commitJob.schedule();

