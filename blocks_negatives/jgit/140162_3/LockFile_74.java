	static final FilenameFilter FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return !name.endsWith(LOCK_SUFFIX);
		}
	};
