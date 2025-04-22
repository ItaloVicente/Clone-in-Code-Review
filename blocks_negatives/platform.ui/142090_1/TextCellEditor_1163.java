        return text.getSelectionCount() > 0
                || text.getCaretPosition() < text.getCharCount();
    }

    /**
     * The <code>TextCellEditor</code>  implementation of this
     * <code>CellEditor</code> method always returns <code>true</code>.
     */
    @Override
