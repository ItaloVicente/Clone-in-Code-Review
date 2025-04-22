	@Override
	@Nullable
	public PrePushHook getPrePushHook(Repository repo
			PrintStream errorStream) {
		if (isEnabled(repo)) {
			return new LfsPrePushHook(repo
		}
		return null;
	}

