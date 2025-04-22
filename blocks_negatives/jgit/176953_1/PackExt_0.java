
	/**
	 * Get all of the PackExt values.
	 *
	 * @return all of the PackExt values.
	 */
	public static PackExt[] values() {
		return VALUES;
	}

	/**
	 * Returns a PackExt for the file extension and registers it in the values
	 * array.
	 *
	 * @param ext
	 *            the file extension.
	 * @return the PackExt for the ext
	 */
	public static synchronized PackExt newPackExt(String ext) {
		PackExt[] dst = new PackExt[VALUES.length + 1];
		for (int i = 0; i < VALUES.length; i++) {
			PackExt packExt = VALUES[i];
			if (packExt.getExtension().equals(ext))
				return packExt;
			dst[i] = packExt;
		}
		if (VALUES.length >= 32)
			throw new IllegalStateException(

		PackExt value = new PackExt(ext, VALUES.length);
		dst[VALUES.length] = value;
		VALUES = dst;
		return value;
	}
