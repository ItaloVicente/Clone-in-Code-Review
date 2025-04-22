	public static final Comparator<Path> PATH_STRING_COMPARATOR = new Comparator<Path>() {
		@Override
		public int compare(Path p1, Path p2) {
			return STRING_ASCENDING_COMPARATOR.compare(
					p1.toAbsolutePath().toString(),
					p2.toAbsolutePath().toString());
		}
	};
