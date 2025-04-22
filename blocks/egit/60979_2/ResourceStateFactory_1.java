	@NonNull
	public IResourceState get(@NonNull IResource resource) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(resource);
		if (indexDiffData == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, resource);
	}

