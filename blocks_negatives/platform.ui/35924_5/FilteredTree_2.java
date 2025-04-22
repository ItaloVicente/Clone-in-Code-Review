	/**
	 * Create a new instance of the receiver.
	 *
	 * @param parent
	 *            the parent <code>Composite</code>
	 * @param treeStyle
	 *            the style bits for the <code>Tree</code>
	 * @param filter
	 *            the filter to be used
	 * @param useNewLook
	 *            ignored, look introduced in 3.5 is always used
	 * @since 3.5
	 *
	 * @deprecated use FilteredTree(Composite parent, int treeStyle,
	 *             PatternFilter filter)
	 */
	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter,
			boolean useNewLook) {
		this(parent, treeStyle, filter);
	}

