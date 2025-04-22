	public static Comparator<FileDiff> PATH_COMPARATOR = new Comparator<FileDiff>() {
		public int compare(FileDiff o1, FileDiff o2) {
			return o1.getPath().compareTo(o2.getPath());
		}
	};

