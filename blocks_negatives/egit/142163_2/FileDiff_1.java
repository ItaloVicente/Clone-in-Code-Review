	public static final Comparator<FileDiff> PATH_COMPARATOR = (FileDiff left, FileDiff right) -> {
		String leftPath = left.getPath();
		String rightPath = right.getPath();
		int i = leftPath.lastIndexOf('/');
		int j = rightPath.lastIndexOf('/');
		int p = leftPath.substring(0, i + 1).replace('/', '\001').compareTo(
				rightPath.substring(0, j + 1).replace('/', '\001'));
		if (p != 0) {
			return p;
		}
		return leftPath.substring(i + 1)
				.compareTo(rightPath.substring(j + 1));
	};
