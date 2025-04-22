			try {
				RebaseResult rebaseRes = rebase.setUpstream(commitToMerge)
						.setProgressMonitor(monitor).setOperation(
								Operation.BEGIN).call();
				result = new PullResult(fetchRes, remote, rebaseRes);
			} catch (NoHeadException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (RefNotFoundException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (JGitInternalException e) {
				throw new JGitInternalException(e.getMessage(), e);
			} catch (GitAPIException e) {
				throw new JGitInternalException(e.getMessage(), e);
			}
