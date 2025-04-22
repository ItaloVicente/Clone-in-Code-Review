
	static final FileFilter FILTER = new FileFilter() {
		public boolean accept(File path) {
			return !path.getName().endsWith(SUFFIX);
		}
	};

