	private void doRebaseOperation(Repository repository,
			RebaseCommand.Operation operation, String jobname) {
		final RebaseOperation op = new RebaseOperation(repository, operation);
		JobUtil.scheduleUserJob(op, jobname, JobFamilies.REBASE,
				new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						RebaseResultDialog.show(op.getResult(),
								currentRepository);
					}
				});
	}

	protected void rebaseAbort() {
		doRebaseOperation(currentRepository, Operation.ABORT,
				UIText.AbortRebaseCommand_JobName);
	}

	protected void rebaseSkip() {
		doRebaseOperation(currentRepository, Operation.SKIP,
				UIText.SkipRebaseCommand_JobName);
	}

	protected void rebaseContinue() {
		doRebaseOperation(currentRepository, Operation.CONTINUE,
				UIText.ContinueRebaseCommand_JobName);
	}

