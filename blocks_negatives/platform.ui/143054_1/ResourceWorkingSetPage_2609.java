        if (workingSet == null) {
        }
        this.workingSet = workingSet;
        if (getShell() != null && text != null) {
            firstCheck = true;
            initializeCheckedState();
            text.setText(workingSet.getName());
        }
    }

    /**
     * Sets the checked state of the container's members.
     *
     * @param container the container whose children should be checked/unchecked
     * @param state true=check all members in the container. false=uncheck all
     * 	members in the container.
     * @param checkExpandedState true=recurse into sub-containers and set the
     * 	checked state. false=only set checked state of members of this container
     */
    private void setSubtreeChecked(IContainer container, boolean state,
            boolean checkExpandedState) {
        if (container.isAccessible() == false
                || (tree.getExpandedState(container) == false && state && checkExpandedState)) {
            return;
        }
        IResource[] members = null;
        try {
            members = container.members();
        } catch (CoreException ex) {
            handleCoreException(
                    ex,
                    getShell(),
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error,
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error_updateCheckedState);
        }
        for (int i = members.length - 1; i >= 0; i--) {
            IResource element = members[i];
            boolean elementGrayChecked = tree.getGrayed(element)
                    || tree.getChecked(element);

            if (state) {
                tree.setChecked(element, true);
                tree.setGrayed(element, false);
            } else {
                tree.setGrayChecked(element, false);
            }
            if (element instanceof IContainer && (state || elementGrayChecked)) {
                setSubtreeChecked((IContainer) element, state, true);
            }
        }
    }

    /**
     * Check and gray the resource parent if all resources of the
     * parent are checked.
     *
     * @param child the resource whose parent checked state should
     * 	be set.
     */
    private void updateParentState(IResource child) {
        if (child == null || child.getParent() == null) {
