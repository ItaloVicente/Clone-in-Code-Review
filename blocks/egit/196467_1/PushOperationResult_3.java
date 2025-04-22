	public void setHookOutput(String stdout, String stderr) {
		hookOut = stdout;
		hookErr = stderr;
	}

	public String getHookStdOut() {
		return hookOut == null ? "" : hookOut; //$NON-NLS-1$
	}

	public String getHookStdErr() {
		return hookErr == null ? "" : hookErr; //$NON-NLS-1$
	}

