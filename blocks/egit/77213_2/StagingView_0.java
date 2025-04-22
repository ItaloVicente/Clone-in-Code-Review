	private void prepareAmend(boolean amend) {
		String jobname = NLS.bind(UIText.ResetAction_reset, HEADS_FIRST_PARENT);
		final ResetOperation operation;
		if (amend) {
			amendedCommit = Activator.getDefault().getRepositoryUtil()
					.parseHeadCommit(currentRepository);
			operation = new ResetOperation(currentRepository, HEADS_FIRST_PARENT,
					ResetType.SOFT);
		} else {
			if (amendedCommit == null) {
				throw new IllegalStateException(
						"amendedCommit should have been set on previous amend"); //$NON-NLS-1$
			}
			operation = new ResetOperation(currentRepository,
					amendedCommit.name(), ResetType.SOFT);
		}
		operation.disableReflog(true);
		JobUtil.scheduleUserJob(operation, jobname, JobFamilies.RESET);
	}

