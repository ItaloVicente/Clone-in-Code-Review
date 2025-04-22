	public static DirCache read(ObjectReader reader
			throws IOException {
		DirCache d = newInCore();
		DirCacheBuilder b = d.builder();
		b.addTree(null
		b.finish();
		return d;
	}

