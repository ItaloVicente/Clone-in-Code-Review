	public CommitCommand setHookErrorStream(String hookName
			PrintStream hookStdErr) {
		if (!(PreCommitHook.NAME.equals(hookName)
				|| CommitMsgHook.NAME.equals(hookName)
				|| PostCommitHook.NAME.equals(hookName))) {
			throw new IllegalArgumentException(MessageFormat
					.format(JGitText.get().illegalHookName
		}
		hookErrRedirect.put(hookName
		return this;
	}

