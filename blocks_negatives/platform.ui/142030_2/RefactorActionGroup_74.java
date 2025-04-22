        textActionHandler.setCopyAction(copyAction);
        textActionHandler.setPasteAction(pasteAction);
        textActionHandler.setDeleteAction(deleteAction);
        renameAction.setTextActionHandler(textActionHandler);

        actionBars.setGlobalActionHandler(ActionFactory.MOVE.getId(),
                moveAction);
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
    }

    /**
     * Handles a key pressed event by invoking the appropriate action.
     */
    @Override
