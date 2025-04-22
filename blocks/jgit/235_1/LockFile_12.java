
	static final FilenameFilter FILTER = new FilenameFilter() {
		public boolean accept(File dir
			return !name.endsWith(SUFFIX);
		}
	};

