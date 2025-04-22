	/**
	 * @param path
	 * @return target path of the symlink
	 * @throws IOException
	 */
	public static String readSymlink(File path) throws IOException {
		Path nioPath = path.toPath();
		Path target = Files.readSymbolicLink(nioPath);
		String targetString = target.toString();
		if (SystemReader.getInstance().isWindows())
			targetString = targetString.replace('\\', '/');
		else if (SystemReader.getInstance().isMacOS())
			targetString = Normalizer.normalize(targetString, Form.NFC);
		return targetString;
	}

	/**
	 * @param path
	 *            path of the symlink to be created
	 * @param target
	 *            target of the symlink to be created
	 * @throws IOException
	 */
	public static void createSymLink(File path, String target)
			throws IOException {
		Path nioPath = path.toPath();
		if (Files.exists(nioPath, LinkOption.NOFOLLOW_LINKS))
			Files.delete(nioPath);
		if (SystemReader.getInstance().isWindows())
			target = target.replace('/', '\\');
		Path nioTarget = new File(target).toPath();
		Files.createSymbolicLink(nioPath, nioTarget);
	}

