    /**
     * Creates an instance of this class.
     *
     * @param window the workbench window in which this action appears
     */
    public SavePerspectiveAction(IWorkbenchWindow window) {
        super(window);
        setText(WorkbenchMessages.SavePerspective_text);
        setActionDefinitionId(IWorkbenchCommandConstants.WINDOW_SAVE_PERSPECTIVE_AS);
        setToolTipText(WorkbenchMessages.SavePerspective_toolTip);
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_ACTION);
    }
