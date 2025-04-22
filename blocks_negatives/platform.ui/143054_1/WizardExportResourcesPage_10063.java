    private IStructuredSelection initialResourceSelection;

    private List selectedTypes = new ArrayList();

    private ResourceTreeAndListGroup resourceGroup;

    private boolean showLinkedResources;

    private final static String SELECT_TYPES_TITLE = IDEWorkbenchMessages.WizardTransferPage_selectTypes;

    private final static String SELECT_ALL_TITLE = IDEWorkbenchMessages.WizardTransferPage_selectAll;

    private final static String DESELECT_ALL_TITLE = IDEWorkbenchMessages.WizardTransferPage_deselectAll;

    /**
     * Creates an export wizard page. If the current resource selection
     * is not empty then it will be used as the initial collection of resources
     * selected for export.
     *
     * @param pageName the name of the page
     * @param selection {@link IStructuredSelection} of {@link IResource}
     * @see IDE#computeSelectedResources(IStructuredSelection)
     */
    protected WizardExportResourcesPage(String pageName,
            IStructuredSelection selection) {
        super(pageName);
        this.initialResourceSelection = selection;
    }

    /**
     * The <code>addToHierarchyToCheckedStore</code> implementation of this
     * <code>WizardDataTransferPage</code> method returns <code>false</code>.
     * Subclasses may override this method.
     */
    @Override
