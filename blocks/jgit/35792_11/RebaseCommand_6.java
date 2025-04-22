	public RebaseCommand setHookErrorHandler(
			HookFailureHandler hookFailureHandler) {
		this.hookFailureHandler = hookFailureHandler;
		return this;
	}

	public RebaseCommand setHookOutputStream(PrintStream hookStdOut) {
		this.hookOutRedirect = hookStdOut;
		return this;
	}

