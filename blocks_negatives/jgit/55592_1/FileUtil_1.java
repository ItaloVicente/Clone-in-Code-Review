	static Attributes getFileAttributesBasic(FS fs, File path) {
		try {
			Path nioPath = path.toPath();
			BasicFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath,
							BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(fs, path,
					true,
					readAttributes.isDirectory(),
					fs.supportsExecute() ? path.canExecute() : false,
					readAttributes.isSymbolicLink(),
					readAttributes.isRegularFile(), //
					readAttributes.creationTime().toMillis(), //
					readAttributes.lastModifiedTime().toMillis(),
					readAttributes.isSymbolicLink() ? Constants
							.encode(FileUtils.readSymLink(path)).length
							: readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(path, fs);
		}
	}

