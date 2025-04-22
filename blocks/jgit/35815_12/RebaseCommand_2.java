	public RebaseCommand setNoVerify(boolean noVerify) {
		this.noVerify = noVerify;
		return this;
	}

	public RebaseCommand setHookOutputStream(PrintStream hookStdOut) {
		this.hookOutRedirect = hookStdOut;
		return this;
	}

