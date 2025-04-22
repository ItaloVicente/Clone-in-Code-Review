
		boolean enabled = evaluateEnabled(context, window);
		if (window != null) {
			Shell shell = window.getShell();
			if (shell != null && !shell.isDisposed()) {
				shell.setModified(enabled);
			}
		}

		return enabled ? EvaluationResult.TRUE : EvaluationResult.FALSE;
	}

	private boolean evaluateEnabled(IEvaluationContext context, IWorkbenchWindow window) {

