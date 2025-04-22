
	public static GarbageCollectCommand getGarbageCollectCommand(
			@NonNull Repository repository) {
		try (Git git = new Git(toFileRepository(repository))) {
			return git.gc();
		}
	}

	@SuppressWarnings("restriction")
	private static @NonNull Repository toFileRepository(
			@NonNull Repository repository) {
		Repository toConvert = repository;
		if (toConvert instanceof RepositoryHandle) {
			toConvert = ((RepositoryHandle) toConvert).getDelegate();
		}
		if (toConvert instanceof org.eclipse.jgit.internal.storage.file.FileRepository) {
			return toConvert;
		}
		throw new IllegalStateException(
				"Repository is not a FileRepository: " + repository); //$NON-NLS-1$
	}
