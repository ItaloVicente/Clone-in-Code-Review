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
