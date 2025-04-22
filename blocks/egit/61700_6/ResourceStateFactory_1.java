	@NonNull
	public IResourceState get(@NonNull IResource resource) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(resource);
		if (indexDiffData == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, resource);
	}

	@NonNull
	public IResourceState get(@NonNull File file) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(file);
		if (indexDiffData == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, file);
	}

