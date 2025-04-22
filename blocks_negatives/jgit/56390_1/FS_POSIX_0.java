	@Override
	public boolean isSymLink(File path) throws IOException {
		return FileUtil.isSymlink(path);
	}

	@Override
	public long lastModified(File path) throws IOException {
		return FileUtil.lastModified(path);
	}

	@Override
	public void setLastModified(File path, long time) throws IOException {
		FileUtil.setLastModified(path, time);
	}

	@Override
	public long length(File f) throws IOException {
		return FileUtil.getLength(f);
	}

	@Override
	public boolean exists(File path) {
		return FileUtil.exists(path);
	}

	@Override
	public boolean isDirectory(File path) {
		return FileUtil.isDirectory(path);
	}

	@Override
	public boolean isFile(File path) {
		return FileUtil.isFile(path);
	}

	@Override
	public boolean isHidden(File path) throws IOException {
		return FileUtil.isHidden(path);
	}

