	private IWorkbenchWindow workbenchWindow;

	private PerspectiveTracker tracker;

	public NewWizardAction(IWorkbenchWindow window) {
		super(WorkbenchMessages.NewWizardAction_text);
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		tracker = new PerspectiveTracker(window, this);
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setToolTipText(WorkbenchMessages.NewWizardAction_toolTip);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.NEW_ACTION);
	}

	@Deprecated
