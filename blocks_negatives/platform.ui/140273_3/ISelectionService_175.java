    /**
     * Adds a part-specific selection listener which is notified when selection changes
     * in the part with the given id. This is independent of part activation - the part
     * need not be active for notification to be sent.
     * <p>
     * When the part is created, the listener is passed the part's initial selection.
     * When the part is disposed, the listener is passed a <code>null</code> selection,
     * but only if the listener implements <code>INullSelectionListener</code>.
     * </p>
     * <p>
     * Note: This will not correctly track editor parts as each editor does
     * not have a unique partId.
     * </p>
