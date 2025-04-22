	@Override
	PrintWriter createErrorWriter() {
		if (errw == null) {
			errw = new PrintWriter(result.err);
		}
		return errw;
	}

	void init(final TextBuiltin cmd) throws IOException {
		cmd.outs = result.out;
		cmd.errs = result.err;
		super.init(cmd);
	}

	@Override
	protected Repository openGitDir(String aGitdir) throws IOException {
		assertNull(aGitdir);
		return db;
	}

	@Override
	void exit(int status
		if (t == null) {
			t = new IllegalStateException(Integer.toString(status));
