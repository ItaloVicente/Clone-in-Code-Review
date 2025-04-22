        getViewer().getTree().setFocus();
    }

    /**
     * Note: For experimental use only.
     * Sets the decorator for the navigator.
     *
     * @param decorator a label decorator or <code>null</code> for no decorations.
     */
    public void setLabelDecorator(ILabelDecorator decorator) {
        DecoratingLabelProvider provider = (DecoratingLabelProvider) getViewer()
                .getLabelProvider();
        if (decorator == null) {
            IDecoratorManager manager = getSite().getWorkbenchWindow()
                    .getWorkbench().getDecoratorManager();
            provider.setLabelDecorator(manager.getLabelDecorator());
        } else {
            provider.setLabelDecorator(decorator);
        }
    }

    /**
     * Updates the message shown in the status line.
     *
     * @param selection the current selection
     */
    void updateStatusLine(IStructuredSelection selection) {
        String msg = getStatusLineMessage(selection);
        getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
    }

    /**
     * Updates the title text and title tool tip.
     * Called whenever the input of the viewer changes.
     */
    void updateTitle() {
        Object input = getViewer().getInput();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (input == null || input.equals(workspace)
                || input.equals(workspace.getRoot())) {
        } else {
            ILabelProvider labelProvider = (ILabelProvider) getViewer()
                    .getLabelProvider();
            setContentDescription(labelProvider.getText(input));
            setTitleToolTip(getToolTipText(input));
        }
    }
