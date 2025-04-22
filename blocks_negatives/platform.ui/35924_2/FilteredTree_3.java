		this.parent = parent;
	}

	/**
	 * Create a new instance of the receiver. Subclasses that wish to override
	 * the default creation behavior may use this constructor, but must ensure
	 * that the <code>init(composite, int, PatternFilter)</code> method is
	 * called in the overriding constructor.
	 *
	 * @param parent
	 *            the parent <code>Composite</code>
	 * @param useNewLook
	 *            ignored, look introduced in 3.5 is always used
	 * @see #init(int, PatternFilter)
	 *
	 * @since 3.5
	 *
	 * @deprecated use FilteredTree(Composite parent) instead
	 */
	@Deprecated
	protected FilteredTree(Composite parent, boolean useNewLook) {
		this(parent);
