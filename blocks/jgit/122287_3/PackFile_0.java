		this(getPackFileNames(new PackFileName(packFile)
	}

	public PackFile(ConcurrentHashMap<PackExt
		this.names = names;
		this.packLastModified = (int) (getPackFile().lastModified() >> 10);
