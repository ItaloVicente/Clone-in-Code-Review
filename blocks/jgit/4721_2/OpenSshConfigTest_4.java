		assertTrue(h.isBatchMode());
	}

	private static class FS_Mock extends FS {

		private File homeDir;

		private FS_Mock(File homeDir) {
			this.homeDir = homeDir;
		}

		@Override
		public File userHome() {
			return homeDir;
		}

		@Override
		public FS newInstance() {
			return null;
		}

		@Override
		public boolean supportsExecute() {
			return false;
		}

		@Override
		public boolean canExecute(File f) {
			return false;
		}

		@Override
		public boolean setExecute(File f
			return false;
		}

		@Override
		public boolean retryFailedLockFileCommit() {
			return false;
		}

		@Override
		protected File discoverGitPrefix() {
			return null;
		}

		@Override
		public ProcessBuilder runInShell(String cmd
			return null;
		}
