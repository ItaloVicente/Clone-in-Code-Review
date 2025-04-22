	@NonNull
	public IResourceState get(@Nullable IResource resource) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(resource);
		if (indexDiffData == null || resource == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, resource);
	}

	@NonNull
	public IResourceState get(@Nullable File file) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(file);
		if (indexDiffData == null || file == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, file);
	}

