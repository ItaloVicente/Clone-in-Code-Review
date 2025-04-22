		return UNKNOWN_STATE;
	}

	@NonNull
	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull File file) {
		return get(indexDiffData, new FileItem(file));
