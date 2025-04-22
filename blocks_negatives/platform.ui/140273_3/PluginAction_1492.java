        if (delegate == null) {
            createDelegate();
            if (delegate == null) {
                MessageDialog
                        .openInformation(
                                Util.getShellToParentOn(),
                                WorkbenchMessages.Information,
                                WorkbenchMessages.PluginAction_operationNotAvailableMessage);
                return;
            }
            if (!isEnabled()) {
                MessageDialog.openInformation(Util.getShellToParentOn(), WorkbenchMessages.Information,
                        WorkbenchMessages.PluginAction_disabledMessage);
                return;
            }
        }

        if (event != null) {
            if (delegate instanceof IActionDelegate2) {
                ((IActionDelegate2) delegate).runWithEvent(this, event);
                return;
            }
            if (delegate instanceof IActionDelegateWithEvent) {
                ((IActionDelegateWithEvent) delegate).runWithEvent(this, event);
                return;
            }
        }

        delegate.run(this);
    }

    /**
     * Handles selection change. If rule-based enabled is
     * defined, it will be first to call it. If the delegate
     * is loaded, it will also be given a chance.
     *
     * @param newSelection the new selection
     */
    public void selectionChanged(ISelection newSelection) {
        selection = newSelection;
        if (selection == null) {
