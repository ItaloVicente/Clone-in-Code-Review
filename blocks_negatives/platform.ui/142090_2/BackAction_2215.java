    /**
     * Constructs a new action for the specified frame list.
     *
     * @param frameList the frame list
     */
    public BackAction(FrameList frameList) {
        super(frameList);
        setText(FrameListMessages.Back_text);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
