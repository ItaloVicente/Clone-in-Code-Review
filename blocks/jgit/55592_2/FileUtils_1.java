
	static boolean isSymlink(File path) {
		Path nioPath = path.toPath();
		return Files.isSymbolicLink(nioPath);
	}

	static long lastModified(File path) throws IOException {
		Path nioPath = path.toPath();
		return Files.getLastModifiedTime(nioPath
				.toMillis();
	}

	static void setLastModified(File path
		Path nioPath = path.toPath();
		Files.setLastModifiedTime(nioPath
	}

	static boolean exists(File path) {
		Path nioPath = path.toPath();
		return Files.exists(nioPath
	}

	static boolean isHidden(File path) throws IOException {
		Path nioPath = path.toPath();
		return Files.isHidden(nioPath);
	}

	public static void setHidden(File path
		Path nioPath = path.toPath();
		Files.setAttribute(nioPath
				LinkOption.NOFOLLOW_LINKS);
	}

	public static long getLength(File path) throws IOException {
		Path nioPath = path.toPath();
		if (Files.isSymbolicLink(nioPath))
			return Files.readSymbolicLink(nioPath).toString()
					.getBytes(Constants.CHARSET).length;
		return Files.size(nioPath);
	}

	static boolean isDirectory(File path) {
		Path nioPath = path.toPath();
		return Files.isDirectory(nioPath
	}

	static boolean isFile(File path) {
		Path nioPath = path.toPath();
		return Files.isRegularFile(nioPath
	}

	public static boolean canExecute(File path) {
		if (!isFile(path)) {
			return false;
		}
		return path.canExecute();
	}

	static Attributes getFileAttributesBasic(FS fs
		try {
			Path nioPath = path.toPath();
			BasicFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							BasicFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(fs
					true
					readAttributes.isDirectory()
					fs.supportsExecute() ? path.canExecute() : false
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.isSymbolicLink() ? Constants
							.encode(readSymLink(path)).length
							: readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(path
		}
	}

	public static Attributes getFileAttributesPosix(FS fs
		try {
			Path nioPath = path.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(
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
		} catch (IOException e) {
			return new Attributes(path
		}
	}

	public static File normalize(File file) {
		if (SystemReader.getInstance().isMacOS()) {
			String normalized = Normalizer.normalize(file.getPath()
					Normalizer.Form.NFC);
			return new File(normalized);
		}
		return file;
	}

	public static String normalize(String name) {
		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			return Normalizer.normalize(name
		}
		return name;
	}
