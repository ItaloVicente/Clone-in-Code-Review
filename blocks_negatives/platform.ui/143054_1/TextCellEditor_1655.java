            int pos = text.getCaretPosition();
            if (pos < text.getCharCount()) {
                text.setSelection(pos, pos + 1);
            }
        }
        checkSelection();
        checkDeleteable();
        checkSelectable();
    }

    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method pastes the
     * the clipboard contents over the current selection.
     */
    @Override
