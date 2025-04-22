
	public FilteredTree(Composite parent, int treeStyle, PatternFilter filter, boolean useNewLook,
			boolean useFastHashLookup) {
		super(parent, SWT.NONE);
		this.parent = parent;
		init(treeStyle, filter);
		treeViewer.setUseHashlookup(useFastHashLookup);
	}

