            if (text != null && !text.isDisposed()
                    && (text.getStyle() & SWT.MULTI) != 0) {
                if ((keyEvent.stateMask & SWT.CTRL) != 0) {
                    super.keyReleaseOccured(keyEvent);
                }
            }
            return;
        }
        super.keyReleaseOccured(keyEvent);
    }

    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method copies the
     * current selection to the clipboard.
     */
    @Override
