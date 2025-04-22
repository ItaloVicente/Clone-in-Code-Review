	/**
	 * Set reuse objects configuration option for this writer. If enabled,
	 * writer searches for representation in a pack file. If possible,
	 * compressed data is directly copied from such a pack file. Data checksum
	 * is verified.
	 * <p>
	 * Default setting: {@link #DEFAULT_REUSE_OBJECTS}
	 * </p>
	 *
	 * @param reuseObjects
	 *            boolean indicating whether or not writer should reuse existing
	 *            objects representation.
	 */
	public void setReuseObjects(boolean reuseObjects) {
		this.reuseObjects = reuseObjects;
