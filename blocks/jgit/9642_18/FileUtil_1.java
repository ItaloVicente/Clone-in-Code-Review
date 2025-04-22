	static Attributes getFileAttributes(FS fs
		try {
			Path nioPath = path.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new FileUtil.Java7PosixAttributes(fs
					nioPath
							.permissions().contains(
									PosixFilePermission.OWNER_EXECUTE)
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
							.creationTime().toMillis()
							.lastModifiedTime().toMillis());
			return attributes;
		} catch (NoSuchFileException e) {
			return new FileUtil.Java7PosixAttributes(fs
					false
		} catch (IOException e) {
			return new Attributes(path
		}
	}

