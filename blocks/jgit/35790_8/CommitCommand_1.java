	private void executePreCommitHookIfVerifyOn() throws RejectCommitException {
		if (noVerify) {
			return;
		}

		final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
		final PrintStream hookErrRedirect = new PrintStream(errorByteArray);
		ProcessResult preCommitHookResult = FS.DETECTED.runIfPresent(repo
				Hook.PRE_COMMIT
				hookErrRedirect
		if (preCommitHookResult.isExecutedWithError()) {
			String errorMessage = MessageFormat.format(
					JGitText.get().commitRejectedByHook
					Hook.PRE_COMMIT.getName()
			throw new RejectCommitException(errorMessage);
		}
	}

	private void executeCommitMsgHookIfVerifyOn() throws IOException
			RejectCommitException {
		if (noVerify || FS.DETECTED.findHook(repo
			return;
		}

		exportPreparedMessage(message);

		final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
		final PrintStream hookErrRedirect = new PrintStream(errorByteArray);


		final String messageFilePath = getMessageFile(false).getAbsolutePath()
				.replace(File.separatorChar
		ProcessResult commitMsgHookResult = FS.DETECTED.runIfPresent(repo
				Hook.COMMIT_MSG
				hookOutRedirect
		final String errorDetails = errorByteArray.toString();
		if (commitMsgHookResult.isExecutedWithError()) {
			String errorMessage = MessageFormat.format(
					JGitText.get().commitRejectedByHook
					Hook.COMMIT_MSG.getName()
			throw new RejectCommitException(errorMessage);
		}

		message = readPreparedMessage();
	}

