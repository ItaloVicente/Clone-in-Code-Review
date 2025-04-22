		super(UIText.SkipRebaseCommand_CancelDialogMessage,
				UIText.RebaseCommandHandler_CancelDialogTitle);
	}

	@Override
	public RebaseOperation getRebaseOperation(ExecutionEvent event) {
		return new RebaseOperation(
				AbstractSharedCommandHandler.getRepository(event),
				Operation.SKIP);
	}

	@Override
	public String getJobName(RebaseOperation operation) {
		return UIText.SkipRebaseCommand_JobName;
