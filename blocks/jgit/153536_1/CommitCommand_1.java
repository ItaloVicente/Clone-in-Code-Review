	public CommitCommand setHookErrorStream(PrintStream hookStdErr) {
		setHookErrorStream(PreCommitHook.NAME
		setHookErrorStream(CommitMsgHook.NAME
		setHookErrorStream(PostCommitHook.NAME
		return this;
	}

