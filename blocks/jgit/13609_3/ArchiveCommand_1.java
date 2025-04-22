	public ArchiveCommand setFilename(String filename) {
		int slash = filename.lastIndexOf('/');
		int dot = filename.indexOf('.'

		if (dot == -1)
			this.suffix = "";
		else
			this.suffix = filename.substring(dot);
		return this;
	}

