	public PushCommand setHookOutputStream(PrintStream redirect) {
		checkCallable();
		hookOutRedirect = redirect;
		return this;
	}

	public PushCommand setHookErrorStream(PrintStream redirect) {
		checkCallable();
		hookErrRedirect = redirect;
		return this;
	}

