	@Override
	public boolean isSymLink(File path) throws IOException {
		return FileUtils.isSymlink(path);
	}

	@Override
	public long lastModified(File path) throws IOException {
		return FileUtils.lastModified(path);
	}

	@Override
	public void setLastModified(File path, long time) throws IOException {
		FileUtils.setLastModified(path, time);
	}

	@Override
	public long length(File f) throws IOException {
		return FileUtils.getLength(f);
	}

	@Override
	public boolean exists(File path) {
		return FileUtils.exists(path);
	}

	@Override
	public boolean isDirectory(File path) {
		return FileUtils.isDirectory(path);
	}

	@Override
	public boolean isFile(File path) {
		return FileUtils.isFile(path);
	}

	@Override
	public boolean isHidden(File path) throws IOException {
		return FileUtils.isHidden(path);
	}

	@Override
	public void setHidden(File path, boolean hidden) throws IOException {
		FileUtils.setHidden(path, hidden);
	}

	/**
	 * @since 3.3
	 */
	@Override
	public Attributes getAttributes(File path) {
		return FileUtils.getFileAttributesBasic(this, path);
	}

