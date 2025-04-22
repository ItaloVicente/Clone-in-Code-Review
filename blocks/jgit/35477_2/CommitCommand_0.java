
			if (!noVerify) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				int preCommitHookResult = FS.DETECTED.runIfPresent(repo
						Hook.PRE_COMMIT
						hookErrRedirect
				final String errorDetails = errorByteArray.toString();
				if (preCommitHookResult != 0) {
					commitRejectedByHook(Hook.PRE_COMMIT
				}
			}

