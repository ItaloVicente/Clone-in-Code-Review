        text.cut();
        checkSelection();
        checkDeleteable();
        checkSelectable();
    }

    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method deletes the
     * current selection or, if there is no selection,
     * the character next character from the current position.
     */
    @Override
