	/**
	 * Helper for accessing tree/blob/index methods.
	 *
	 * @param i
	 * @return '/' for Tree entries and NUL for non-treeish objects
	 */
	final public static int lastChar(Entry i) {
		return FileMode.TREE.equals(i.getModeBits()) ? '/' : '\0';
	}

