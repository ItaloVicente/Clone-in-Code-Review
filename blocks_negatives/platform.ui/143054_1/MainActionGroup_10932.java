        super.setContext(context);
        gotoGroup.setContext(context);
        openGroup.setContext(context);
        refactorGroup.setContext(context);
        sortAndFilterGroup.setContext(context);
        workspaceGroup.setContext(context);
        undoRedoGroup.setContext(context);
    }

    /**
     * Fills the context menu with the actions contained in this group
     * and its subgroups.
     *
     * @param menu the context menu
     */
    @Override
