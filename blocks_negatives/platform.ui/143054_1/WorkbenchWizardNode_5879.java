public abstract class WorkbenchWizardNode implements IWizardNode,
        IPluginContribution {
    protected WorkbenchWizardSelectionPage parentWizardPage;

    protected IWizard wizard;

    protected IWizardDescriptor wizardElement;

    /**
     * Creates a <code>WorkbenchWizardNode</code> that holds onto a wizard
     * element.  The wizard element provides information on how to create
     * the wizard supplied by the ISV's extension.
     *
     * @param aWizardPage the wizard page
     * @param element the wizard descriptor
     */
    public WorkbenchWizardNode(WorkbenchWizardSelectionPage aWizardPage,
    		IWizardDescriptor element) {
        super();
        this.parentWizardPage = aWizardPage;
        this.wizardElement = element;
    }

    /**
     * Returns the wizard represented by this wizard node.  <b>Subclasses</b>
     * must override this method.
     *
     * @return the wizard object
     * @throws CoreException
     */
    public abstract IWorkbenchWizard createWizard() throws CoreException;

    @Override
