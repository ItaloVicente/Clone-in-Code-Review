	public static final FileSnapshot MISSING_FILE = new FileSnapshot(0
		@Override
		public boolean isModified(File path) {
			return path.exists();
		}
	};

