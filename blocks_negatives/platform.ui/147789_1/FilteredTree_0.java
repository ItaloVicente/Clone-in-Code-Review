	/**
	 * Create a new instance of the receiver.
	 * <p>
	 * <b>WARNING:</b> Using this constructor results in a slow performing tree and
	 * should not be used if the underlying data model uses a stable and correct
	 * hashCode and equals implementation. Prefer the usage of
	 * {@link #FilteredTree(Composite, int, PatternFilter, boolean, true)} if
	 * possible.
	 * </p>
	 *
	 * @param parent    the parent <code>Composite</code>
	 * @param treeStyle the style bits for the <code>Tree</code>
	 * @param filter    the filter to be used
	 *
	 * @deprecated As of 3.116, replaced by
	 *             {@link #FilteredTree(Composite, int, PatternFilter, boolean, boolean)}
	 *
	 *
	 */
	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}

	/**
	 * Create a new instance of the receiver.
	 *
	 * <p>
	 * <b>WARNING:</b> Using this constructor results in a slow performing tree and
	 * should not be used if the underlying data model uses a stable and correct
	 * hashCode and equals implementation. Prefer the usage of
	 * {@link #FilteredTree(Composite, int, PatternFilter, boolean, true)} if
	 * possible
	 * </p>
	 *
	 * @param parent     the parent <code>Composite</code>
	 * @param treeStyle  the style bits for the <code>Tree</code>
	 * @param filter     the filter to be used
	 * @param useNewLook ignored, look introduced in 3.5 is always used
	 * @since 3.5
	 * @deprecated As of 3.116, replaced by
	 *             {@link #FilteredTree(Composite, int, PatternFilter, boolean, boolean)}
	 *
	 */
	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter, boolean useNewLook) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}


