
	static boolean isSymlink(File file) {
		return Files.isSymbolicLink(file.toPath());
	}

	static long lastModified(File file) throws IOException {
		return Files.getLastModifiedTime(file.toPath()
				.toMillis();
	}

	static void setLastModified(File file
		Files.setLastModifiedTime(file.toPath()
	}

	static boolean exists(File file) {
		return Files.exists(file.toPath()
	}

	static boolean isHidden(File file) throws IOException {
		return Files.isHidden(file.toPath());
	}

	public static void setHidden(File file
		Files.setAttribute(file.toPath()
				LinkOption.NOFOLLOW_LINKS);
	}

	public static long getLength(File file) throws IOException {
		Path nioPath = file.toPath();
		if (Files.isSymbolicLink(nioPath))
			return Files.readSymbolicLink(nioPath).toString()
					.getBytes(Constants.CHARSET).length;
		return Files.size(nioPath);
	}

	static boolean isDirectory(File file) {
		return Files.isDirectory(file.toPath()
	}

	static boolean isFile(File file) {
		return Files.isRegularFile(file.toPath()
	}

	public static boolean canExecute(File file) {
		if (!isFile(file)) {
			return false;
		}
		return Files.isExecutable(file.toPath());
	}

	static Attributes getFileAttributesBasic(FS fs
		try {
			Path nioPath = file.toPath();
			BasicFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							BasicFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(fs
					true
					readAttributes.isDirectory()
					fs.supportsExecute() ? file.canExecute() : false
					readAttributes.isSymbolicLink()
					readAttributes.isRegularFile()
					readAttributes.creationTime().toMillis()
					readAttributes.lastModifiedTime().toMillis()
					readAttributes.isSymbolicLink() ? Constants
							.encode(readSymLink(file)).length
							: readAttributes.size());
			return attributes;
		} catch (IOException e) {
			return new Attributes(file
		}
	}

	public static Attributes getFileAttributesPosix(FS fs
		try {
			Path nioPath = file.toPath();
			PosixFileAttributes readAttributes = nioPath
					.getFileSystem()
					.provider()
					.getFileAttributeView(nioPath
							PosixFileAttributeView.class
							LinkOption.NOFOLLOW_LINKS).readAttributes();
			Attributes attributes = new Attributes(
					fs
					file
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
			return new Attributes(file
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
