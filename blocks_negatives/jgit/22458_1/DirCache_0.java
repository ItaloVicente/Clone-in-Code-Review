	 * <p>
	 * If there is at least one entry in the index for this path the position of
	 * the lowest stage is returned. Subsequent stages can be identified by
	 * testing consecutive entries until the path differs.
	 * <p>
	 * If no path matches the entry -(position+1) is returned, where position is
	 * the location it would have gone within the index.
