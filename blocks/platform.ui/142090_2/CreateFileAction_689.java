		super(IDEWorkbenchMessages.CreateFileAction_toolTip);
		Assert.isNotNull(provider);
		shellProvider = provider;
		initAction();
	}
	private void initAction(){
		setToolTipText(IDEWorkbenchMessages.CreateFileAction_toolTip);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FILE));
		setId(ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
