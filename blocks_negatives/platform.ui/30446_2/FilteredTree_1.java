	/**
	 * Create a new instance of the receiver.
	 * 
	 * @param parent
	 *            the parent <code>Composite</code>
	 * @param treeStyle
	 *            the style bits for the <code>Tree</code>
	 * @param filter
	 *            the filter to be used
	 * 
	 * @deprecated As of 3.5, replaced by
	 *             {@link #FilteredTree(Composite, int, PatternFilter, boolean)}
	 *             where using the new look is encouraged
	 */
	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}

