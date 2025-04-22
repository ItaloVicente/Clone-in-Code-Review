    /**
     * Create a ResourceNavigatorMoveAction and use the supplied viewer to update the UI.
     * @param shell Shell
     * @param structureViewer StructuredViewer
     */
    public ResourceNavigatorMoveAction(Shell shell,
            StructuredViewer structureViewer) {
        super(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                INavigatorHelpContextIds.RESOURCE_NAVIGATOR_MOVE_ACTION);
        this.viewer = structureViewer;
        this.moveProjectAction = new MoveProjectAction(shell);
    }
