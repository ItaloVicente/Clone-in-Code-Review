    /**
     * Creates a new <code>ToggleEditorsVisibilityAction</code>
     *
     * @param window the window
     */
    public ToggleEditorsVisibilityAction(IWorkbenchWindow window) {
        super(window);
        setText(WorkbenchMessages.ToggleEditor_hideEditors);
        setToolTipText(WorkbenchMessages.ToggleEditor_toolTip);
        window.getWorkbench().getHelpSystem().setHelp(this,
                IWorkbenchHelpContextIds.TOGGLE_EDITORS_VISIBILITY_ACTION);
        window.addPerspectiveListener(this);
    }
