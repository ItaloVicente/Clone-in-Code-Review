		this(getPackFileNames(new PackFileName(packFile)
	}

	public PackFile(ConcurrentHashMap<PackExt
		this.names = names;
		this.fileSnapshot = PackFileSnapshot.save(names.get(PACK));
