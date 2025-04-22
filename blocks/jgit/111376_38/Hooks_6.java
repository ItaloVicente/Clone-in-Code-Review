		if (LFS.getInstance().isAvailable()) {
			PrePushHook hook = LFS.getInstance().getPrePushHook(repo
					outputStream);
			if (hook != null) {
				if (hook.isNativeHookPresent()) {
					throw new IllegalStateException(MessageFormat
							.format(JGitText.get().lfsHookConflict
				}
				return hook;
