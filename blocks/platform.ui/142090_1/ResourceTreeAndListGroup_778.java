	private Object currentTreeSelection;
	private Collection expandedTreeNodes = new HashSet();
	private Map checkedStateStore = new HashMap(9);
	private HashSet whiteCheckedTreeItems = new HashSet();
	private ITreeContentProvider treeContentProvider;
	private IStructuredContentProvider listContentProvider;
	private ILabelProvider treeLabelProvider;
	private ILabelProvider listLabelProvider;

	private CheckboxTreeViewer treeViewer;
	private CheckboxTableViewer listViewer;

	private static int PREFERRED_HEIGHT = 150;

	public ResourceTreeAndListGroup(Composite parent, Object rootObject,
			ITreeContentProvider treeContentProvider,
			ILabelProvider treeLabelProvider,
			IStructuredContentProvider listContentProvider,
			ILabelProvider listLabelProvider, int style, boolean useHeightHint) {

		root = rootObject;
		this.treeContentProvider = treeContentProvider;
		this.listContentProvider = listContentProvider;
		this.treeLabelProvider = treeLabelProvider;
		this.listLabelProvider = listLabelProvider;
		createContents(parent, style, useHeightHint);
	}

	protected void aboutToOpen() {
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

	private boolean areAllChildrenWhiteChecked(Object treeElement) {
		Object[] children = treeContentProvider.getChildren(treeElement);
		for (int i = 0; i < children.length; ++i) {
			if (!whiteCheckedTreeItems.contains(children[i])) {
