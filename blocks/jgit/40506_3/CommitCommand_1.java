
			if (!noVerify) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				ProcessResult preCommitHookResult = FS.DETECTED.runIfPresent(
						repo
						hookErrRedirect
				if (preCommitHookResult.getStatus() == ProcessResult.Status.OK
						&& preCommitHookResult.getExitCode() != 0) {
					String errorMessage = MessageFormat.format(
							JGitText.get().commitRejectedByHook
							errorByteArray.toString());
					throw new RejectCommitException(errorMessage);
				}
			}

