	private void executePostRewriteCommitHookWithFailureHandler(ObjectId oldId
			RevCommit revCommit) {
		if (!amend || oldId == null || noPostRewrite) {
			return;
		}

		final ObjectId newId = revCommit.getId();
		final String rewritten = oldId.getName() + ' ' + newId.getName() + '\n';

		if (hookFailureHandler == null) {
			executePostRewriteCommitHook(rewritten
		} else {
			final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
			final PrintStream hookErrRedirect = new PrintStream(errorByteArray);

			ProcessResult postRewriteHookResult = executePostRewriteCommitHook(
					rewritten

			if (postRewriteHookResult.isExecutedWithError()) {
				hookFailureHandler.hookExecutionFailed(Hook.POST_REWRITE
						postRewriteHookResult
			}
		}
	}

	private ProcessResult executePostRewriteCommitHook(String stdinArgs
			PrintStream errorStream) {
		ProcessResult result = FS.DETECTED.runIfPresent(repo
				Hook.POST_REWRITE
				errorStream
		return result;
	}

