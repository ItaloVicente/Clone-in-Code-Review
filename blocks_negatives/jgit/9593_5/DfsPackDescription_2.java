	/** @return name of the pack file. */
	public String getPackName() {
		return packName;
	}

	/** @return name of the index file. */
	public String getIndexName() {
		String name = getPackName();
		int dot = name.lastIndexOf('.');
		if (dot < 0)
			dot = name.length();
		return name.substring(0, dot) + ".idx"; //$NON-NLS-1$
