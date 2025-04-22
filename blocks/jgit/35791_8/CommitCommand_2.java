	private void executePostCommitHookWithFailureHandler() {
		if (hookFailureHandler == null) {
			executePostCommitHook(System.err);
		} else {
			final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
			final PrintStream hookErrRedirect = new PrintStream(errorByteArray);

			ProcessResult postCommitHookResult = executePostCommitHook(hookErrRedirect);

			if (postCommitHookResult.isExecutedWithError()) {
				hookFailureHandler.hookExecutionFailed(Hook.POST_COMMIT
						postCommitHookResult
			}
		}
	}

	private ProcessResult executePostCommitHook(PrintStream errorStream) {
		ProcessResult postCommitHookResult = FS.DETECTED.runIfPresent(repo
				Hook.POST_COMMIT
				null);
		return postCommitHookResult;
	}

