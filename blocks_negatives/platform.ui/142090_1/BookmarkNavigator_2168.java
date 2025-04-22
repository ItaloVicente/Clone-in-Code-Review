    }

    /**
     * Notifies this listener that the menu is about to be shown by
     * the given menu manager.
     *
     * @param manager the menu manager
     */
    void fillContextMenu(IMenuManager manager) {
        manager.add(openAction);
        manager.add(copyAction);
        updatePasteEnablement();
        manager.add(pasteAction);
        manager.add(removeAction);
        manager.add(selectAllAction);
        manager.add(showInNavigatorAction);
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        manager.add(new Separator());
        manager.add(editAction);
    }

    @Override
