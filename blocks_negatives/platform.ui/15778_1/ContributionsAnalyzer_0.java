		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {
				ret[0] = ref.evaluate(eContext) != EvaluationResult.FALSE;
			}

			public void handleException(Throwable exception) {
				trace("isVisible exception", exception); //$NON-NLS-1$
			}
		});
