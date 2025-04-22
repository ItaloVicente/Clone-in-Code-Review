        setSite(site);
        setInput(input);
    }

    /**
     * Returns whether the contents of this editor have changed since the last save
     * operation.
     * <p>
     * Subclasses must override this method to implement the open-save-close lifecycle
     * for an editor.  For greater details, see <code>IEditorPart</code>
     * </p>
     *
     * @see IEditorPart
     */
    @Override
