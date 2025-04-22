        initialize();
        checkState();
        return fieldEditorParent;
    }

    /**
     * Creates the page's field editors.
     * <p>
     * The default implementation of this framework method
     * does nothing. Subclass must implement this method to
     * create the field editors.
     * </p>
     * <p>
     * Subclasses should call <code>getFieldEditorParent</code>
     * to obtain the parent control for each field editor.
     * This same parent should not be used for more than
     * one editor as the parent may change for each field
     * editor depending on the layout style of the page
     * </p>
     */
    protected abstract void createFieldEditors();

    /**
     * The field editor preference page implementation of an <code>IDialogPage</code>
     * method disposes of this page's controls and images.
     * Subclasses may override to release their own allocated SWT
     * resources, but must call <code>super.dispose</code>.
     */
    @Override
