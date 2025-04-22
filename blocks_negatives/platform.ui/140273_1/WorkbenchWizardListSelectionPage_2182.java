public abstract class WorkbenchWizardListSelectionPage extends
        WorkbenchWizardSelectionPage implements ISelectionChangedListener,
        IDoubleClickListener {


    private static final int SIZING_LISTS_HEIGHT = 200;

    private static final String STORE_SELECTED_WIZARD_ID = DIALOG_SETTING_SECTION_NAME

    private TableViewer viewer;

    private String message;

    /**
     * Creates a <code>WorkbenchWizardListSelectionPage</code>.
     *
     * @param aWorkbench the current workbench
     * @param currentSelection the workbench's current resource selection
     * @param wizardElements the collection of <code>WorkbenchWizardElements</code>
     *            to display for selection
     * @param message the message to display above the selection list
     * @param triggerPointId the trigger point id
     */
    protected WorkbenchWizardListSelectionPage(IWorkbench aWorkbench,
            IStructuredSelection currentSelection,
            AdaptableList wizardElements, String message, String triggerPointId) {
        super(
                "singleWizardSelectionPage", aWorkbench, currentSelection, wizardElements, triggerPointId); //$NON-NLS-1$
        setDescription(WorkbenchMessages.WizardList_description);
        this.message = message;
    }

    @Override
