	private RevFilter treeRevFilter(String path) {
		return new TreeRevFilter(rw, treeFilter(path));
	}

	private static TreeFilter treeFilter(String path) {
		return AndTreeFilter.create(
				PathFilterGroup.createFromStrings(Collections.singleton(path)),
				TreeFilter.ANY_DIFF);
