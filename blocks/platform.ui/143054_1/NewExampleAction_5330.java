	public NewExampleAction(IWorkbenchWindow window) {
		super(IDEWorkbenchMessages.NewExampleAction_text);
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.window = window;
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setToolTipText(IDEWorkbenchMessages.NewExampleAction_toolTip);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
