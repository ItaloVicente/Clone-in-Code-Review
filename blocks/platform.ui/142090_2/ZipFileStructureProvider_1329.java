		return new Path(((ZipEntry) element).getName()).lastSegment();
	}

	public ZipEntry getRoot() {
		return root;
	}

	public ZipFile getZipFile() {
		return zipFile;
	}

	protected void initialize() {
