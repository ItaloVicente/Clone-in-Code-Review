	private Object root;

	private Object currentTreeSelection;

	private List expandedTreeNodes = new ArrayList();

	private Map checkedStateStore = new HashMap(9);

	private List whiteCheckedTreeItems = new ArrayList();

	private ITreeContentProvider treeContentProvider;

	private IStructuredContentProvider listContentProvider;

	private ILabelProvider treeLabelProvider;

	private ILabelProvider listLabelProvider;

	private ViewerComparator treeComparator;

	private ViewerComparator listComparator;

	private CheckboxTreeViewer treeViewer;

	private CheckboxTableViewer listViewer;


	public CheckboxTreeAndListGroup(Composite parent, Object rootObject,
			ITreeContentProvider treeContentProvider,
			ILabelProvider treeLabelProvider,
			IStructuredContentProvider listContentProvider,
			ILabelProvider listLabelProvider, int style, int width, int height) {

		this(parent, rootObject, treeContentProvider, treeLabelProvider, null,
				listContentProvider, listLabelProvider, null, style, width, height);
	}

	public CheckboxTreeAndListGroup(Composite parent, Object rootObject,
			ITreeContentProvider treeContentProvider,
			ILabelProvider treeLabelProvider,
			ViewerComparator treeComparator,
			IStructuredContentProvider listContentProvider,
			ILabelProvider listLabelProvider,
			ViewerComparator listComparator,
			int style, int width, int height) {

		root = rootObject;
		this.treeContentProvider = treeContentProvider;
		this.listContentProvider = listContentProvider;
		this.treeLabelProvider = treeLabelProvider;
		this.listLabelProvider = listLabelProvider;
		this.treeComparator = treeComparator;
		this.listComparator = listComparator;
		createContents(parent, width, height, style);
	}

	public void aboutToOpen() {
		determineWhiteCheckedDescendents(root);
		checkNewTreeElements(treeContentProvider.getElements(root));
		currentTreeSelection = null;

		Object[] elements = treeContentProvider.getElements(root);
		Object primary = elements.length > 0 ? elements[0] : null;
		if (primary != null) {
			treeViewer.setSelection(new StructuredSelection(primary));
		}
		treeViewer.getControl().setFocus();
	}

	public void addCheckStateListener(ICheckStateListener listener) {
		addListenerObject(listener);
	}

	private void addToHierarchyToCheckedStore(Object treeElement) {

		if (!checkedStateStore.containsKey(treeElement)) {
