	public BackAction(FrameList frameList) {
		super(frameList);
		setId(ID);
		setText(FrameListMessages.Back_text);
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
		setDisabledImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
