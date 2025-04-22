	/**
	 * Adds another file as a source of ignore rules for this file. The
	 * secondary file will have a lower priority than the first file, and
	 * the parent directory of this node will be regarded as firstFile.getParent()
	 *
	 * @param f
	 * 			Secondary source of gitignore information for this node
	 */
	public void addSecondarySource(File f) {
		secondaryFile = f;
