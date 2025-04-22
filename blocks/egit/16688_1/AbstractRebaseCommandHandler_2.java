	protected Object execute(final RebaseOperation rebase)
			throws ExecutionException {
		String jobname = getJobName(rebase);
		final Repository repository = rebase.getRepository();
		final RebaseCommand.Operation operation = rebase.getOperation();

