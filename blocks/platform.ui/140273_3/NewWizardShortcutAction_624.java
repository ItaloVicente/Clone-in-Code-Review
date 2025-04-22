public class NewWizardShortcutAction extends Action implements IPluginContribution {
	private IWizardDescriptor wizardElement;

	private static final int SIZING_WIZARD_WIDTH = 500;

	private static final int SIZING_WIZARD_HEIGHT = 500;

	private IWorkbenchWindow window;

	public NewWizardShortcutAction(IWorkbenchWindow window, IWizardDescriptor wizardDesc) {
		super(wizardDesc.getLabel());
		setToolTipText(wizardDesc.getDescription());
		setImageDescriptor(wizardDesc.getImageDescriptor());
		setId(ActionFactory.NEW.getId());
		wizardElement = wizardDesc;
		this.window = window;
	}

	public IWizardDescriptor getWizardDescriptor() {
