		final boolean[] ret = new boolean[1];
		ret[0] = false;
		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {
				ret[0] = ref.evaluate(eContext) != EvaluationResult.FALSE;
			}

			public void handleException(Throwable exception) {
				trace("isVisible exception", exception); //$NON-NLS-1$
			}
		});
		return ret[0];
