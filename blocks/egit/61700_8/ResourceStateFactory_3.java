	@NonNull
	private IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull FileSystemItem file) {
		IPath path = file.getAbsolutePath();
		if (path == null) {
			return UNKNOWN_STATE;
