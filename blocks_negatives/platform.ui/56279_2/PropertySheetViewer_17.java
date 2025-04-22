        return input;
    }

    /**
     * Returns the root entry for this property sheet viewer. The root entry is
     * not visible in the viewer.
     *
     * @return the root entry or <code>null</code>.
     */
    public IPropertySheetEntry getRootEntry() {
        return rootEntry;
    }

    /**
     * The <code>PropertySheetViewer</code> implementation of this
     * <code>ISelectionProvider</code> method returns the result as a
     * <code>StructuredSelection</code>.
     * <p>
     * Note that this method only includes <code>IPropertySheetEntry</code> in
     * the selection (no categories).
     * </p>
     */
    @Override
