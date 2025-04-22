	private boolean noReadPermissions(Object file) {
		if (file instanceof File) {
			java.nio.file.Path path = ((File) file).toPath();
			boolean readable = Files.isReadable(((File) file).toPath());
			boolean regular = false;
			try {
				BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class);
				regular = readAttributes.isRegularFile();
			} catch (IOException e) {
				return false;
			}
			if (regular && !readable) {
				return true;
			}
		}
		return false;
	}

