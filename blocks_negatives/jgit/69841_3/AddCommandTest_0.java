		TreeWalk walk = TreeWalk.forPath(db, path, commit1.getTree());
		assertNotNull(walk);
		assertEquals(FileMode.EXECUTABLE_FILE, walk.getFileMode(0));

		FS nonExecutableFs = new FS() {

			public boolean supportsExecute() {
				return false;
			}

			public boolean setExecute(File f, boolean canExec) {
				return false;
			}

			public ProcessBuilder runInShell(String cmd, String[] args) {
				return null;
			}

			public boolean retryFailedLockFileCommit() {
				return false;
			}

			public FS newInstance() {
				return this;
			}

			protected File discoverGitExe() {
				return null;
			}

			public boolean canExecute(File f) {
				return false;
			}

			@Override
			public boolean isCaseSensitive() {
				return false;
			}
		};
