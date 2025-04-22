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
