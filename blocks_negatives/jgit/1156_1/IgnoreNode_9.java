
		if (i > -1 && rules.get(i) != null)
			return rules.get(i).getResult();

		return false;
	}

	/**
	 * @return
	 * 			  True if the previous call to isIgnored resulted in a match,
	 * 			  false otherwise.
	 */
	public boolean wasMatched() {
		return matched;
	}

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
	}

	/**
	 * Clear all rules in this node.
	 */
	public void clear() {
		rules.clear();
	}

	/**
	 * @param val
	 * 			  Set the last modified time of this node.
	 */
	public void setLastModified(long val) {
		lastModified = val;
	}

	/**
	 * @return
	 * 			  Last modified time of this node.
	 */
	public long getLastModified() {
		return lastModified;
