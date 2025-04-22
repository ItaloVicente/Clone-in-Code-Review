     * Constructs a new action for the specified frame list.
     *
     * @param frameList the frame list
     */
    public GoIntoAction(FrameList frameList) {
        super(frameList);
        setId(ID);
        setText(FrameListMessages.GoInto_text);
        setToolTipText(FrameListMessages.GoInto_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
