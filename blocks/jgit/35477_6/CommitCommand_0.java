
			if (!noVerify) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				ProcessResult preCommitHookResult = FS.DETECTED.runIfPresent(
						repo
						Hook.PRE_COMMIT
						hookErrRedirect
				final String errorDetails = errorByteArray.toString();
				if (preCommitHookResult.getStatus() == ProcessResult.Status.OK
						&& preCommitHookResult.getExitCode() != 0) {
					commitRejectedByHook(Hook.PRE_COMMIT
				}
			}

