	public GitCompareFileRevisionEditorInput(ITypedElement left, ITypedElement right, ITypedElement ancestor, IWorkbenchPage page) {
		super(new CompareConfiguration(), page);
		this.left = left;
		this.right = right;
		this.ancestor = ancestor;
	}

