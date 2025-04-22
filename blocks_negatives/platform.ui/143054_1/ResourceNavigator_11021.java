    /**
     * Updates the action bar actions.
     *
     * @param selection the current selection
     * @since 2.0
     */
    protected void updateActionBars(IStructuredSelection selection) {
        ResourceNavigatorActionGroup group = getActionGroup();
        if (group != null) {
            group.setContext(new ActionContext(selection));
            group.updateActionBars();
        }
    }

    /**
     * Updates the message shown in the status line.
     *
     * @param selection the current selection
     */
    protected void updateStatusLine(IStructuredSelection selection) {
        String msg = getStatusLineMessage(selection);
        getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
    }

    /**
     * Updates the title text and title tool tip.
     * Called whenever the input of the viewer changes.
     * Called whenever the input of the viewer changes.
     *
     * @since 2.0
     */
    public void updateTitle() {
        Object input = getViewer().getInput();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkingSet workingSet = workingSetFilter.getWorkingSet();

        if (input == null || input.equals(workspace)
                || input.equals(workspace.getRoot())) {
            if (workingSet != null) {
                setTitleToolTip(NLS.bind(ResourceNavigatorMessages.ResourceNavigator_workingSetToolTip, workingSet.getLabel()));
            } else {
            }
        } else {
            ILabelProvider labelProvider = (ILabelProvider) getTreeViewer()
                    .getLabelProvider();
            String inputToolTip = getFrameToolTipText(input);
            String text = labelProvider.getText(input);
            if(text != null) {
