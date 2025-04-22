        }
        fViewer.setInput(fInput);
        return fViewer;
    }

    /**
     * Returns the tree viewer.
     *
     * @return the tree viewer
     */
    protected CheckboxTreeViewer getTreeViewer() {
        return fViewer;
    }

    /**
     * Adds the selection and deselection buttons to the dialog.
     *
     * @param composite
     *            the parent composite
     * @return Composite the composite the buttons were created in.
     */
    protected Composite createSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 0;
