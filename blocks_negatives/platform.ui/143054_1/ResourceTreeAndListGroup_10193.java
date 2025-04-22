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

    /**
     *	Create an instance of this class.  Use this constructor if you wish to specify
     *	the width and/or height of the combined widget (to only hard-code one of the
     *	sizing dimensions, specify the other dimension's value as -1)
     *
     * @param parent
     * @param rootObject
     * @param treeContentProvider
     * @param treeLabelProvider
     * @param listContentProvider
     * @param listLabelProvider
     * @param style
     * @param useHeightHint If true then use the height hint
     *  to make this group big enough
     *
     */
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

    /**
     * This method must be called just before this window becomes visible.
     * @noreference This method is not intended to be referenced by clients.
     */
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
     *	Return a boolean indicating whether all children of the passed tree element
     *	are currently white-checked
     *
     *	@return boolean
     *	@param treeElement java.lang.Object
     */
    private boolean areAllChildrenWhiteChecked(Object treeElement) {
        Object[] children = treeContentProvider.getChildren(treeElement);
        for (int i = 0; i < children.length; ++i) {
            if (!whiteCheckedTreeItems.contains(children[i])) {
