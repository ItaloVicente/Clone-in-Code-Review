	private void initAction(){
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
		setToolTipText(IDEWorkbenchMessages.CreateFolderAction_toolTip);
		setId(ID);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
