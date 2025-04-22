	public void addFileExt(PackExt ext) {
		extensions |= ext.getBit();
	}

	public boolean hasFileExt(PackExt ext) {
		return (extensions & ext.getBit()) != 0;
	}

