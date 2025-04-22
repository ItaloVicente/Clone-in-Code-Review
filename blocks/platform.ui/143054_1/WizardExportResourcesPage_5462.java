	private IStructuredSelection initialResourceSelection;

	private List selectedTypes = new ArrayList();

	private ResourceTreeAndListGroup resourceGroup;

	private boolean showLinkedResources;

	private static final String SELECT_TYPES_TITLE = IDEWorkbenchMessages.WizardTransferPage_selectTypes;

	private static final String SELECT_ALL_TITLE = IDEWorkbenchMessages.WizardTransferPage_selectAll;

	private static final String DESELECT_ALL_TITLE = IDEWorkbenchMessages.WizardTransferPage_deselectAll;

	protected WizardExportResourcesPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		this.initialResourceSelection = selection;
	}

	@Override
