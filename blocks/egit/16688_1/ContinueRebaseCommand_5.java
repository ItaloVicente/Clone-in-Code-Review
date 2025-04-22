		super(UIText.ContinueRebaseCommand_CancelDialogMessage, UIText.RebaseCommandHandler_CancelDialogTitle);
	}

	@Override
	public RebaseOperation getRebaseOperation(ExecutionEvent event)
			throws ExecutionException {
		return new RebaseOperation(getRepository(event), Operation.CONTINUE);
	}

	@Override
	public String getJobName(RebaseOperation operation) {
		return UIText.ContinueRebaseCommand_JobName;
