	static Attributes getFileAttributesBasic(FS fs
		try {
			Path nioPath = path.toPath();
			BasicFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							BasicFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new FileUtil.Java7BasicAttributes(fs
					true
					readAttributes.isDirectory()
					fs.supportsExecute() ? path.canExecute() : false
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.size());
			return attributes;
		} catch (NoSuchFileException e) {
			return new FileUtil.Java7BasicAttributes(fs
					false
		} catch (IOException e) {
			return new Attributes(path
		}
	}

	static Attributes getFileAttributesPosix(FS fs
		try {
			Path nioPath = path.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new FileUtil.Java7BasicAttributes(
					fs
					path
					true
					readAttributes.isDirectory()
					readAttributes.permissions().contains(
							PosixFilePermission.OWNER_EXECUTE)
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.size());
			return attributes;
		} catch (NoSuchFileException e) {
			return new FileUtil.Java7BasicAttributes(fs
					false
		} catch (IOException e) {
			return new Attributes(path
		}
	}

