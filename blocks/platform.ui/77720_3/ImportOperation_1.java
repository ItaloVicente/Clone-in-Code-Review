	private boolean hasNoReadPermissions(Object file) {
		if (!(file instanceof File)) {
			return false;
		}
		java.nio.file.Path path = ((File) file).toPath();
		boolean readable = Files.isReadable(path);
		boolean regular = Files.isRegularFile(path);
		return regular && !readable;
	}

