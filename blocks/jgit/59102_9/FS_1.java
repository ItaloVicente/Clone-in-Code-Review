	public static class ExecutionResult {
		private TemporaryBuffer stdout;

		private TemporaryBuffer stderr;

		private int rc;

		public ExecutionResult(TemporaryBuffer stdout
				int rc) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.rc = rc;
		}

		public TemporaryBuffer getStdout() {
			return stdout;
		}

		public TemporaryBuffer getStderr() {
			return stderr;
		}

		public int getRc() {
			return rc;
		}
	}

	private final static Logger LOG = LoggerFactory.getLogger(FS.class);
