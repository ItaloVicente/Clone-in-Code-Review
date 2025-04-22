		public Builder setGitDir(File gitDir) {
			File normalizedGitDir = new Path(gitDir.getAbsolutePath()).toFile();
			return super.setGitDir(normalizedGitDir);
		}

		public CachingRepository createRepository() throws IOException {
			CachingRepository repo = new CachingRepository(this);
