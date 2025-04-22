		if (rewrittenList.length() > 0) {
			if (hookFailureHandler != null) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				ProcessResult postRewriteHookResult = FS.DETECTED.runIfPresent(
						repo
						hookOutRedirect
						rewrittenList.toString());

				if (postRewriteHookResult.isExecutedWithError()) {
					hookFailureHandler.hookExecutionFailed(Hook.POST_REWRITE
							postRewriteHookResult
				}
			} else {
				FS.DETECTED.runIfPresent(repo
						new String[] { "rebase" }
						rewrittenList.toString());
			}
			rewrittenList = null;
		}

