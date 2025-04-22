    /**
     * The selected node; <code>null</code> if none.
     */
    private IWizardNode selectedNode = null;

    /**
     * List of wizard nodes that have cropped up in the past
     * (element type: <code>IWizardNode</code>).
     */
    private List<IWizardNode> selectedWizardNodes = new ArrayList<>();

    /**
     * Creates a new wizard selection page with the given name, and
     * with no title or image.
     *
     * @param pageName the name of the page
     */
    protected WizardSelectionPage(String pageName) {
        super(pageName);
        setPageComplete(false);
    }

    /**
     * Adds the given wizard node to the list of selected nodes if
     * it is not already in the list.
     *
     * @param node the wizard node, or <code>null</code>
     */
    private void addSelectedNode(IWizardNode node) {
        if (node == null) {
