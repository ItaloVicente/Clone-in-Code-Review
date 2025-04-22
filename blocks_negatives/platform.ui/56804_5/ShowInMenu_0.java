	/**
	 * Returns the <code>IShowInSource</code> provided by the source part, or
	 * <code>null</code> if it does not provide one.
	 *
	 * @param sourcePart
	 *            the source part
	 * @return an <code>IShowInSource</code> or <code>null</code>
	 */
	private IShowInSource getShowInSource(IWorkbenchPart sourcePart) {
		return Util.getAdapter(sourcePart, IShowInSource.class);
	}

	/**
	 * Returns the <code>IShowInTargetList</code> for the given source part,
	 * or <code>null</code> if it does not provide one.
	 *
	 * @param sourcePart
	 *            the source part or <code>null</code>
	 * @return the <code>IShowInTargetList</code> or <code>null</code>
	 */
	private IShowInTargetList getShowInTargetList(IWorkbenchPart sourcePart) {
		return Util.getAdapter(sourcePart, IShowInTargetList.class);
	}

