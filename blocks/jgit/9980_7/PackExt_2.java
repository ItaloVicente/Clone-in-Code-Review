	public synchronized static PackExt newPackExt(String ext) {
		PackExt[] dst = new PackExt[VALUES.length + 1];
		for (int i = 0; i < VALUES.length; i++) {
			PackExt packExt = VALUES[i];
			if (packExt.getExtension().equals(ext))
				return packExt;
			dst[i] = packExt;
		}
		if (VALUES.length >= 32)
			throw new IllegalStateException(

		PackExt value = new PackExt(ext
		dst[VALUES.length] = value;
		VALUES = dst;
		return value;
	}

	private final String ext;

	private final int pos;

	private PackExt(String ext
