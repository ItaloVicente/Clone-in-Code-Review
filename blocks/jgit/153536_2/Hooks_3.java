
	public static PrePushHook prePush(Repository repo
			PrintStream errorStream) {
		if (LfsFactory.getInstance().isAvailable()) {
			PrePushHook hook = LfsFactory.getInstance().getPrePushHook(repo
					outputStream
			if (hook != null) {
				if (hook.isNativeHookPresent()) {
					PrintStream ps = outputStream;
					if (ps == null) {
						ps = System.out;
					}
					ps.println(MessageFormat
							.format(JGitText.get().lfsHookConflict
				}
				return hook;
			}
		}
		return new PrePushHook(repo
	}
