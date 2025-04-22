	/**
	 * @return size of the reverse index, in bytes.
	 */
	public int getReverseIndexSize() {
		return (int) Math.min(objectCount * 8, Integer.MAX_VALUE);
	}

