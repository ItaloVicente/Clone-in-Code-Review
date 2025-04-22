		setHookOutputStream(PreCommitHook.NAME
		setHookOutputStream(CommitMsgHook.NAME
		setHookOutputStream(PostCommitHook.NAME
		return this;
	}

	public CommitCommand setHookOutputStream(String hookName
			PrintStream hookStdOut) {
		if (!(PreCommitHook.NAME.equals(hookName)
				|| CommitMsgHook.NAME.equals(hookName)
				|| PostCommitHook.NAME.equals(hookName))) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().illegalHookName
							hookName));
		}
		hookOutRedirect.put(hookName
