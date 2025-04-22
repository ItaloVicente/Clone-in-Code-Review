		JobJoiner joiner = JobJoiner.startListening(JobFamilies.PUSH, 20,
				TimeUnit.SECONDS);
		ContextMenuHelper
				.clickContextMenu(project,
						getPushToUpstreamMenuPath(remoteName));
		TestUtil.openJobResultDialog(joiner.join());
