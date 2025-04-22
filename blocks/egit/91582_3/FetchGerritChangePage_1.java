	private CheckoutResult checkout(final String textForBranch,
			IProgressMonitor monitor)
			throws OperationCanceledException, InterruptedException {
		SubMonitor m = SubMonitor.convert(monitor);
		final BranchOperation op = new BranchOperation(repository,
				textForBranch, false);
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		String jobName = MessageFormat.format(UIText.BranchAction_checkingOut,
				repoName, textForBranch);
		final CheckoutResult[] result = new CheckoutResult[1];
		JobUtil.scheduleUserWorkspaceJob(op, jobName, JobFamilies.CHECKOUT,
				new JobChangeAdapter() {

					@Override
					public void done(IJobChangeEvent event) {
						result[0] = op.getResult();
					}
				});
		Job.getJobManager().join(JobFamilies.CHECKOUT, m);
		return result[0];
	}

