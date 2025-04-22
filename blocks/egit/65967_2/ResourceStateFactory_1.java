		Repository repository = ResourceUtil.getRepository(path);
		return getIndexDiffDataOrNull(repository);
	}

	@Nullable
	private IndexDiffData getIndexDiffDataOrNull(
			@Nullable Repository repository) {
