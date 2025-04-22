	@NonNull
	public static Repository[] getRepositories(
			@Nullable IEvaluationContext evaluationContext) {
		return getRepositories(getSelection(evaluationContext));
	}

