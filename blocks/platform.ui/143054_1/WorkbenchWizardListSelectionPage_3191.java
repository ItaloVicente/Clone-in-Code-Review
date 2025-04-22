public abstract class WorkbenchWizardListSelectionPage extends WorkbenchWizardSelectionPage
		implements ISelectionChangedListener, IDoubleClickListener {

	private static final String DIALOG_SETTING_SECTION_NAME = "WizardListSelectionPage."; //$NON-NLS-1$

	private static final int SIZING_LISTS_HEIGHT = 200;

	private static final String STORE_SELECTED_WIZARD_ID = DIALOG_SETTING_SECTION_NAME + "STORE_SELECTED_WIZARD_ID"; //$NON-NLS-1$

	private TableViewer viewer;

	private String message;

	protected WorkbenchWizardListSelectionPage(IWorkbench aWorkbench, IStructuredSelection currentSelection,
			AdaptableList wizardElements, String message, String triggerPointId) {
		super("singleWizardSelectionPage", aWorkbench, currentSelection, wizardElements, triggerPointId); //$NON-NLS-1$
		setDescription(WorkbenchMessages.WizardList_description);
		this.message = message;
	}

	@Override
