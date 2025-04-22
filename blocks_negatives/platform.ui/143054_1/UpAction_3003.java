    /**
     * Constructs a new action for the specified frame list.
     *
     * @param frameList the frame list
     */
    public UpAction(FrameList frameList) {
        super(frameList);
        setText(FrameListMessages.Up_text);
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_UP));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_UP_DISABLED));
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
