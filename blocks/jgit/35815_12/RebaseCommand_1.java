	private void executePreRebaseHookIfVerifyOn() throws RejectRebaseException {
		if (noVerify) {
			return;
		}

		final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
		final PrintStream hookErrRedirect = new PrintStream(errorByteArray);

		final String upstreamName = Repository
				.shortenRefName(upstreamCommitName);


		ProcessResult preRebaseHookResult = FS.DETECTED.runIfPresent(repo
				Hook.PRE_REBASE
				hookOutRedirect

		if (preRebaseHookResult.isExecutedWithError()) {
			String errorMessage = MessageFormat.format(
					JGitText.get().rebaseRejectedByHook
					Hook.PRE_REBASE.getName()
			throw new RejectRebaseException(errorMessage);
		}
	}

