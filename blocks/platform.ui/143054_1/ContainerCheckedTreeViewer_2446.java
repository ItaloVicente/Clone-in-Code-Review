	public ContainerCheckedTreeViewer(Composite parent) {
		super(parent);
		initViewer();
	}

	public ContainerCheckedTreeViewer(Composite parent, int style) {
		super(parent, style);
		initViewer();
	}

	public ContainerCheckedTreeViewer(Tree tree) {
		super(tree);
		initViewer();
	}

	private void initViewer() {
		setUseHashlookup(true);
		addCheckStateListener(event -> doCheckStateChanged(event.getElement()));
		addTreeListener(new ITreeViewerListener() {
			@Override
