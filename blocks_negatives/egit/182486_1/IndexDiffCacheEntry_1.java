	/**
	 * Jobs accessing this code should be configured as "system" jobs, to not
	 * interrupt autobuild jobs, see bug 474003
	 *
	 * @param monitor
	 */
	private void waitForWorkspaceLock(IProgressMonitor monitor) {
		Repository repository = getRepository();
		ISchedulingRule rule;
		if (repository == null) {
			rule = ResourcesPlugin.getWorkspace().getRoot();
		} else {
			rule = RuleUtil.getRule(repository);
		}
		try {
			Job.getJobManager().beginRule(rule, monitor);
		} catch (OperationCanceledException e) {
			return;
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

