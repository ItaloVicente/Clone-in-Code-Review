	@Deprecated
	public static final TreeFilter ANY_DIFF = new AnyDiffFilter(true);

	public static TreeFilter anyDiff(Config cfg) {
		WorkingTreeOptions options = WorkingTreeOptions.KEY.parse(cfg);
		return new AnyDiffFilter(options.isFileMode());
	}
