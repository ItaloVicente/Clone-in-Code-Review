	@Override
	public long getPackSize() {
		if (newPack == null)
			return super.getPackSize();

		File pack = newPack.getPackFile();
		long size = pack.length();
		String p = pack.getAbsolutePath();
		String i = p.substring(0
		File idx = new File(i);
		if (idx.exists() && idx.isFile())
			size += idx.length();
		return size;
	}

