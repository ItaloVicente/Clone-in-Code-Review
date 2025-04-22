        Dialog.applyDialogFont(composite);

        return composite;
    }

    /**
     * Returns the viewer used to show the list.
     *
     * @return the viewer, or <code>null</code> if not yet created
     */
    protected CheckboxTableViewer getViewer() {
        return listViewer;
    }

    /**
     * Initializes this dialog's viewer after it has been laid out.
     */
    private void initializeViewer() {
        listViewer.setInput(inputElement);
    }

    /**
     * The <code>ListSelectionDialog</code> implementation of this
     * <code>Dialog</code> method builds a list of the selected elements for later
     * retrieval by the client and closes this dialog.
     */
    @Override
