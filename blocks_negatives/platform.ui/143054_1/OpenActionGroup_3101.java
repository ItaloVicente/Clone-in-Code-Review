        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        boolean anyResourceSelected = !selection.isEmpty()
                && ResourceSelectionUtil.allResourcesAreOfType(selection,
                        IResource.PROJECT | IResource.FOLDER | IResource.FILE);
        boolean onlyFilesSelected = !selection.isEmpty()
                && ResourceSelectionUtil.allResourcesAreOfType(selection,
                        IResource.FILE);

        if (onlyFilesSelected) {
            openFileAction.selectionChanged(selection);
            menu.add(openFileAction);
            fillOpenWithMenu(menu, selection);
        }

        if (anyResourceSelected) {
            addNewWindowAction(menu, selection);
        }
    }

    /**
     * Adds the OpenWith submenu to the context menu.
     *
     * @param menu the context menu
     * @param selection the current selection
     */
    private void fillOpenWithMenu(IMenuManager menu,
            IStructuredSelection selection) {

        if (selection.size() != 1) {
