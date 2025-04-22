	public ForwardAction(FrameList frameList) {
		super(frameList);
		setId(ID);
		setText(FrameListMessages.Forward_text);
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		setDisabledImageDescriptor(images
				.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD_DISABLED));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
