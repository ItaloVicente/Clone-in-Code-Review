	@Nullable
	public static RepositoryMapping create(@NonNull IContainer mappedContainer,
			@NonNull File gitDir) {
		IPath location = mappedContainer.getLocation();
		if (location == null) {
			return null;
		}
		return new RepositoryMapping(mappedContainer, location, gitDir);
	}

	private RepositoryMapping(@NonNull
	final IContainer mappedContainer, final @NonNull IPath location,
