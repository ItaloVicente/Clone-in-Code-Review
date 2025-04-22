public abstract class WorkbenchWizardNode implements IWizardNode, IPluginContribution {
	protected WorkbenchWizardSelectionPage parentWizardPage;

	protected IWizard wizard;

	protected IWizardDescriptor wizardElement;

	public WorkbenchWizardNode(WorkbenchWizardSelectionPage aWizardPage, IWizardDescriptor element) {
		super();
		this.parentWizardPage = aWizardPage;
		this.wizardElement = element;
	}

	public abstract IWorkbenchWizard createWizard() throws CoreException;

	@Override
