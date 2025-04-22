		PullResult result;
		if (doRebase) {
			RebaseCommand rebase = new RebaseCommand(repo);
			try {
				RebaseResult rebaseRes = rebase.setUpstream(remoteBranchName)
						.setProgressMonitor(monitor).setOperation(
								Operation.BEGIN).call();
				result = new PullResult(fetchRes
			} catch (NoHeadException e) {
				throw new JGitInternalException(e.getMessage()
			} catch (RefNotFoundException e) {
				throw new JGitInternalException(e.getMessage()
			} catch (JGitInternalException e) {
				throw new JGitInternalException(e.getMessage()
			} catch (GitAPIException e) {
				throw new JGitInternalException(e.getMessage()
			}
		} else {
			AnyObjectId commitToMerge;
