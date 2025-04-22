
			if (hookFailureHandler != null) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				ProcessResult postCommitHookResult = FS.DETECTED.runIfPresent(
						repo
						hookErrRedirect
				if (postCommitHookResult.getStatus() == ProcessResult.Status.OK
						&& postCommitHookResult.getExitCode() != 0) {
					if (hookFailureHandler != null) {
						hookFailureHandler.hookExecutionFailed(
								Hook.POST_COMMIT
								errorByteArray.toString());
					}
				}
			} else {
				FS.DETECTED.runIfPresent(repo
						hookOutRedirect
			}

			return revCommit;
