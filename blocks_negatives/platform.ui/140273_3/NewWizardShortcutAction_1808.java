public class NewWizardShortcutAction extends Action implements
        IPluginContribution {
    private IWizardDescriptor wizardElement;

    /**
     * The wizard dialog width
     */
    private static final int SIZING_WIZARD_WIDTH = 500;

    /**
     * The wizard dialog height
     */
    private static final int SIZING_WIZARD_HEIGHT = 500;

    private IWorkbenchWindow window;

    /**
     * Create an instance of this class.
     *
     * @param window the workbench window in which this action will appear
     * @param wizardDesc a wizard element
     */
    public NewWizardShortcutAction(IWorkbenchWindow window,
            IWizardDescriptor wizardDesc) {
        super(wizardDesc.getLabel());
        setToolTipText(wizardDesc.getDescription());
        setImageDescriptor(wizardDesc.getImageDescriptor());
        setId(ActionFactory.NEW.getId());
        wizardElement = wizardDesc;
        this.window = window;
    }

    /**
     * Get the wizard descriptor for this action.
     *
     * @return the wizard descriptor
     */
    public IWizardDescriptor getWizardDescriptor() {
