		super(UIText.AbortRebaseCommand_CancelDialogMessage,
				UIText.RebaseCommandHandler_CancelDialogTitle);
	}

	@Override
	public RebaseOperation getRebaseOperation(ExecutionEvent event)
			throws ExecutionException {
		return new RebaseOperation(getRepository(event), Operation.ABORT);
	}

	@Override
	public String getJobName(RebaseOperation operation)
			throws ExecutionException {
		return UIText.AbortRebaseCommand_JobName;
