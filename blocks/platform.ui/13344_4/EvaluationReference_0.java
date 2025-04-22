	class Runner implements ISafeRunnable {
		public IEvaluationContext localContext;

		public void run() throws Exception {
			try {
				cache = expression.evaluate(localContext) != EvaluationResult.FALSE;
			} catch (CoreException e) {
				Activator.trace(Policy.DEBUG_CMDS, "Failed to calculate active", e); //$NON-NLS-1$
			}
		}

		public void handleException(Throwable exception) {
			if (exception instanceof Error) {
				throw (Error) exception;
			}
			Activator.trace(Policy.DEBUG_CMDS, "Failed with throwable: " + expression, exception); //$NON-NLS-1$
		}
	}

