	private ICheckStateProvider checkStateProvider;

	private TreeItem lastClickedItem = null;

	public CheckboxTreeViewer(Composite parent) {
		this(parent, SWT.BORDER);
	}

	public CheckboxTreeViewer(Composite parent, int style) {
		this(new Tree(parent, SWT.CHECK | style));
	}

	public CheckboxTreeViewer(Tree tree) {
		super(tree);
	}

	@Override
