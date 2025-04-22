    public NavigationHistoryAction(IWorkbenchWindow window, boolean forward) {
        super("", window); //$NON-NLS-1$
        ISharedImages sharedImages = window.getWorkbench().getSharedImages();
        if (forward) {
            setText(WorkbenchMessages.NavigationHistoryAction_forward_text);
            setToolTipText(WorkbenchMessages.NavigationHistoryAction_forward_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.NAVIGATION_HISTORY_FORWARD);
            setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
            setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD_DISABLED));
            setActionDefinitionId(IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY);
        } else {
            setText(WorkbenchMessages.NavigationHistoryAction_backward_text);
            setToolTipText(WorkbenchMessages.NavigationHistoryAction_backward_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.NAVIGATION_HISTORY_BACKWARD);
            setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
            setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));
            setActionDefinitionId(IWorkbenchCommandConstants.NAVIGATE_BACKWARD_HISTORY);
        }
        setEnabled(false);
        this.forward = forward;
        setMenuCreator(new MenuCreator());
    }
