	private File asFile() {
		final IPath location = node.getLocation();
		return location != null ? location.toFile() : null;
	}

	protected byte[] idSubmodule(Entry e) {
		File nodeFile = asFile();
		if (nodeFile != null)
			return idSubmodule(nodeFile, e);
		return super.idSubmodule(e);
	}
