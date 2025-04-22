        IContainer parent = child.getParent();
        boolean childChecked = false;
        IResource[] members = null;
        try {
            members = parent.members();
        } catch (CoreException ex) {
            handleCoreException(
                    ex,
                    getShell(),
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error,
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error_updateCheckedState);
        }
        for (int i = members.length - 1; i >= 0; i--) {
            if (tree.getChecked(members[i]) || tree.getGrayed(members[i])) {
                childChecked = true;
                break;
            }
        }
        tree.setGrayChecked(parent, childChecked);
        updateParentState(parent);
    }

    /**
     * Validates the working set name and the checked state of the
     * resource tree.
     */
    private void validateInput() {
        String errorMessage = null;
        String infoMessage= null;
        String newText = text.getText();

        if (newText.equals(newText.trim()) == false) {
            errorMessage = IDEWorkbenchMessages.ResourceWorkingSetPage_warning_nameWhitespace;
        } else if (firstCheck) {
            firstCheck = false;
            return;
        }
            errorMessage = IDEWorkbenchMessages.ResourceWorkingSetPage_warning_nameMustNotBeEmpty;
        }
        if (errorMessage == null
                && (workingSet == null || newText.equals(workingSet.getName()) == false)) {
