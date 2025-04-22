	/**
	 * Helper for accessing tree/blob/index methods.
	 *
	 * @param i
	 * @return '/' for Tree entries and NUL for non-treeish objects
	 * @deprecated since it depends on deprecated GitIndex, and internal
	 */
	final public static int lastChar(Entry i) {
		return FileMode.TREE.equals(i.getModeBits()) ? '/' : '\0';
	}

