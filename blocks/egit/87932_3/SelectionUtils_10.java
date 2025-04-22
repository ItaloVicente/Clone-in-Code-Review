	@Nullable
	public static Repository[] getRepositories(
			@Nullable IEvaluationContext evaluationContext) {
		return getRepositories(false, getSelection(evaluationContext), null);
	}

