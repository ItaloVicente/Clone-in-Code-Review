			if (!noVerify) {
				final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
				final PrintStream hookErrRedirect = new PrintStream(
						errorByteArray);
				ProcessResult preCommitHookResult = FS.DETECTED.runIfPresent(
						repo, Hook.PRE_COMMIT, new String[0], hookOutRedirect,
						hookErrRedirect, null);
				if (preCommitHookResult.getStatus() == ProcessResult.Status.OK
						&& preCommitHookResult.getExitCode() != 0) {
					String errorMessage = MessageFormat.format(
							JGitText.get().commitRejectedByHook, Hook.PRE_COMMIT.getName(),
							errorByteArray.toString());
					throw new RejectCommitException(errorMessage);
				}
			}
