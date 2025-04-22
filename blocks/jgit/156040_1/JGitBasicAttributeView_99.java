	private BasicFileAttributes attrs = null;

	public JGitBasicAttributeView(final JGitPathImpl path) {
		super(path);
	}

	@Override
	public BasicFileAttributes readAttributes() throws IOException {
		if (attrs == null) {
			attrs = buildAttrs((JGitFileSystem) path.getFileSystem()
		}
		return attrs;
	}

	@Override
	public Class<? extends BasicFileAttributeView>[] viewTypes() {
		return new Class[] { BasicFileAttributeView.class
	}

	private BasicFileAttributes buildAttrs(final JGitFileSystem fs
			throws NoSuchFileException {
		final PathInfo pathInfo = fs.getGit().getPathInfo(branchName

		if (pathInfo == null || pathInfo.getPathType().equals(PathType.NOT_FOUND)) {
			throw new NoSuchFileException(path);
		}

		final Ref ref = fs.getGit().getRef(branchName);

		return new BasicFileAttributes() {

			private FileTime lastModifiedDate = null;
			private FileTime creationDate = null;

			@Override
			public FileTime lastModifiedTime() {
				if (lastModifiedDate == null) {
					try {
						lastModifiedDate = FileTime
								.fromMillis(fs.getGit().getLastCommit(ref).getCommitterIdent().getWhen().getTime());
					} catch (final Exception e) {
						lastModifiedDate = FileTime.fromMillis(0);
					}
				}
				return lastModifiedDate;
			}

			@Override
			public FileTime lastAccessTime() {
				return lastModifiedTime();
			}

			@Override
			public FileTime creationTime() {
				if (creationDate == null) {
					try {
						creationDate = FileTime
								.fromMillis(fs.getGit().getFirstCommit(ref).getCommitterIdent().getWhen().getTime());
					} catch (final Exception e) {
						creationDate = FileTime.fromMillis(0);
					}
				}
				return creationDate;
			}

			@Override
			public boolean isRegularFile() {
				return pathInfo.getPathType().equals(PathType.FILE);
			}

			@Override
			public boolean isDirectory() {
				return pathInfo.getPathType().equals(PathType.DIRECTORY);
			}

			@Override
			public boolean isSymbolicLink() {
				return false;
			}

			@Override
			public boolean isOther() {
				return false;
			}

			@Override
			public long size() {
				return pathInfo.getSize();
			}

			@Override
			public Object fileKey() {
				return pathInfo.getObjectId() == null ? null : pathInfo.getObjectId().toString();
			}
		};
	}
