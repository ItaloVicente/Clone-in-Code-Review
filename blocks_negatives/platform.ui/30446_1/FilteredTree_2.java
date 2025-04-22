	 * @since 3.3
	 * @deprecated As of 3.5, replaced by
	 *             {@link #FilteredTree(Composite, boolean)} where using the
	 *             look is encouraged
	 */
	@Deprecated
	protected FilteredTree(Composite parent) {
		super(parent, SWT.NONE);
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
	 *            <code>true</code> if the new 3.5 look should be used
	 * @see #init(int, PatternFilter)
	 * 
