        }
        return modifyListener;
    }

    /**
     * Handles a default selection event from the text control by applying the editor
     * value and deactivating this cell editor.
     *
     * @param event the selection event
     *
     * @since 3.0
     */
    protected void handleDefaultSelection(SelectionEvent event) {
        fireApplyEditorValue();
        deactivate();
    }

    /**
     * The <code>TextCellEditor</code>  implementation of this
     * <code>CellEditor</code> method returns <code>true</code> if
     * the current selection is not empty.
     */
    @Override
