
	@Override
	public boolean supportsSymlinks() {
		if (supportSymlinks == null)
			detectSymlinkSupport();
		return Boolean.TRUE.equals(supportSymlinks);
	}

	private void detectSymlinkSupport() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("tempsymlinktarget"
			File linkName = new File(tempFile.getParentFile()
			FileUtil.createSymLink(linkName
			supportSymlinks = Boolean.TRUE;
			linkName.delete();
		} catch (IOException | UnsupportedOperationException e) {
			supportSymlinks = Boolean.FALSE;
		} finally {
			if (tempFile != null)
				try {
					FileUtils.delete(tempFile);
				} catch (IOException e) {
				}
		}
	}

	@Override
	public boolean isSymLink(File path) throws IOException {
		return FileUtil.isSymlink(path);
	}

	@Override
	public long lastModified(File path) throws IOException {
		return FileUtil.lastModified(path);
	}

	@Override
	public void setLastModified(File path
		FileUtil.setLastModified(path
	}

	@Override
	public void delete(File path) throws IOException {
		FileUtil.delete(path);
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

	@Override
	public void setHidden(File path
		FileUtil.setHidden(path
	}

	@Override
	public String readSymLink(File path) throws IOException {
		return FileUtil.readSymlink(path);
	}

	@Override
	public void createSymLink(File path
		FileUtil.createSymLink(path
	}

	@Override
	public Attributes getAttributes(File path) {
		return FileUtil.getFileAttributesBasic(this
	}
