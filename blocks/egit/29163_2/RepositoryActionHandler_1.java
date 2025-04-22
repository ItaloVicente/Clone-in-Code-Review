		return getRepository(selection);
	}

	static Repository getRepository(IEvaluationContext evaluationContext) {
		return getRepository(getSelection(evaluationContext));
	}

	private static Repository getRepository(IStructuredSelection selection) {
