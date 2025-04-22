    /**
     * The workbench window; or <code>null</code> if this
     * action has been <code>dispose</code>d.
     */
    private IWorkbenchWindow workbenchWindow;

    /**
     * Tracks perspective activation, to update this action's
     * enabled state.
     */
    private PerspectiveTracker tracker;

    /**
     * Create a new instance of this class.
     * @param window
     */
    public NewWizardAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.NewWizardAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        tracker = new PerspectiveTracker(window, this);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
        setToolTipText(WorkbenchMessages.NewWizardAction_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.NEW_ACTION);
    }

    /**
     * Create a new instance of this class
     *
     * @deprecated use the constructor <code>NewWizardAction(IWorkbenchWindow)</code>
     */
    @Deprecated
