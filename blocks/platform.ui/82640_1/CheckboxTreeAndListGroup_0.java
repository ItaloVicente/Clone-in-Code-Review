		this(parent, rootObject, treeContentProvider, treeLabelProvider, null, listContentProvider, listLabelProvider,
				null, style, width, height);
	}

	public CheckboxTreeAndListGroup(Composite parent, Object rootObject, ITreeContentProvider treeContentProvider,
			ILabelProvider treeLabelProvider, ViewerComparator treeComparator,
			IStructuredContentProvider listContentProvider, ILabelProvider listLabelProvider,
			ViewerComparator listComparator, int style, int width, int height) {

		root = rootObject;
		this.treeContentProvider = treeContentProvider;
		this.listContentProvider = listContentProvider;
		this.treeLabelProvider = treeLabelProvider;
		this.listLabelProvider = listLabelProvider;
		this.treeComparator = treeComparator;
		this.listComparator = listComparator;
		createContents(parent, width, height, style);
