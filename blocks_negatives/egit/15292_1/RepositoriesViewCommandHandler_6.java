	protected void enableWorkingDirCommand(Object evaluationContext) {
		if (!(evaluationContext instanceof IEvaluationContext)) {
			setBaseEnabled(false);
			return;
		}
		IEvaluationContext context = (IEvaluationContext) evaluationContext;
		Object selection = context
				.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (!(selection instanceof TreeSelection)) {
			setBaseEnabled(false);
			return;
		}
