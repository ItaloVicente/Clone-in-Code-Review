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


    /**
     *  Create an instance of this class.  Use this constructor if you wish to specify
     *	the width and/or height of the combined widget (to only hardcode one of the
     *	sizing dimensions, specify the other dimension's value as -1)
     * @param parent
     * @param rootObject
     * @param treeContentProvider
     * @param treeLabelProvider
     * @param listContentProvider
     * @param listLabelProvider
     * @param style
     * @param width
     * @param height
     */
    public CheckboxTreeAndListGroup(Composite parent, Object rootObject,
            ITreeContentProvider treeContentProvider,
            ILabelProvider treeLabelProvider,
            IStructuredContentProvider listContentProvider,
            ILabelProvider listLabelProvider, int style, int width, int height) {

    	this(parent, rootObject, treeContentProvider, treeLabelProvider, null,
    			listContentProvider, listLabelProvider, null, style, width, height);
    }

    /**
     *  Create an instance of this class.  Use this constructor if you wish to specify
     *	the width and/or height of the combined widget (to only hardcode one of the
     *	sizing dimensions, specify the other dimension's value as -1)
     * @param parent
     * @param rootObject
     * @param treeContentProvider
     * @param treeLabelProvider
     * @param treeComparator
     * @param listContentProvider
     * @param listLabelProvider
     * @param listComparator
     * @param style
     * @param width
     * @param height
     */
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

    /**
     * This method must be called just before this window becomes visible.
     */
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

    /**
     *	Add the passed listener to self's collection of clients
     *	that listen for changes to element checked states
     *
     *	@param listener ICheckStateListener
     */
    public void addCheckStateListener(ICheckStateListener listener) {
        addListenerObject(listener);
    }

    /**
     *	Add the receiver and all of it's ancestors to the checkedStateStore if they
     * are not already there.
     */
    private void addToHierarchyToCheckedStore(Object treeElement) {

        if (!checkedStateStore.containsKey(treeElement)) {
