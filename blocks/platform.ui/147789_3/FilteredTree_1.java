	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}
	@Deprecated
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter, boolean useNewLook) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
	}


