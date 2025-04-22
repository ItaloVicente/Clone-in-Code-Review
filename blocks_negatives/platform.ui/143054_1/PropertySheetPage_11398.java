        super.setActionBars(actionBars);
        cellEditorActionHandler = new CellEditorActionHandler(actionBars);
        cellEditorActionHandler.setCopyAction(copyAction);
    }

    /**
     * Sets focus to a part in the page.
     */
    @Override
