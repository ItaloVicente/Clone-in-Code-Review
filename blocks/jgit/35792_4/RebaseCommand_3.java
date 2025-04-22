		if (rewrittenList.length() > 0) {
			ProcessResult postRewriteHookResult = FS.DETECTED.runIfPresent(
					repo
					System.out
			if (postRewriteHookResult.getStatus() == ProcessResult.Status.OK
					&& postRewriteHookResult.getExitCode() != 0) {
			}
			rewrittenList = null;
		}

