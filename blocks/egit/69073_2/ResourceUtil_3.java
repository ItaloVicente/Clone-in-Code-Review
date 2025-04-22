	@Nullable
	public static IPath getRepositoryRelativePath(@Nullable IPath path,
			@NonNull Repository repository) {
		if (path == null || repository.isBare()) {
			return null;
		}
		java.nio.file.Path workingDirectory = repository.getWorkTree().toPath();
		java.nio.file.Path toRelativize = path.toFile().toPath();
		if (toRelativize.startsWith(workingDirectory)) {
			int n = workingDirectory.getNameCount();
			int m = toRelativize.getNameCount();
			if (n == m) {
				return new Path(""); //$NON-NLS-1$
			}
			return Path.fromOSString(toRelativize.subpath(n, m).toString());
		}
		return null;
	}
