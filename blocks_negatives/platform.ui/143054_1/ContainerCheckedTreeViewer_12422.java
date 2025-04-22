    /**
     * Constructor for ContainerCheckedTreeViewer.
     * @see CheckboxTreeViewer#CheckboxTreeViewer(Composite)
     */
    public ContainerCheckedTreeViewer(Composite parent) {
        super(parent);
        initViewer();
    }

    /**
     * Constructor for ContainerCheckedTreeViewer.
     * @see CheckboxTreeViewer#CheckboxTreeViewer(Composite,int)
     */
    public ContainerCheckedTreeViewer(Composite parent, int style) {
        super(parent, style);
        initViewer();
    }

    /**
     * Constructor for ContainerCheckedTreeViewer.
     * @see CheckboxTreeViewer#CheckboxTreeViewer(Tree)
     */
    public ContainerCheckedTreeViewer(Tree tree) {
        super(tree);
        initViewer();
    }

    private void initViewer() {
        setUseHashlookup(true);
        addCheckStateListener(event -> doCheckStateChanged(event.getElement()));
        addTreeListener(new ITreeViewerListener() {
            @Override
