		try {
			Path nioPath = path.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath,
							PosixFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(
					fs,
					path,
					true, //
					readAttributes.isDirectory(), //
					readAttributes.permissions().contains(
							PosixFilePermission.OWNER_EXECUTE),
					readAttributes.isSymbolicLink(),
					readAttributes.isRegularFile(), //
					readAttributes.creationTime().toMillis(), //
					readAttributes.lastModifiedTime().toMillis(),
					readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(path, fs);
		}
