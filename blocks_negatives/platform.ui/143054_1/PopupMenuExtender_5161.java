    /**
     * Determines whether this extender would be the same as another extender
     * created with the given values. Two extenders are equivalent if they have
     * the same menu manager, selection provider and part (i.e., if the menu
     * they represent is about to show, they would populate it with duplicate
     * values).
     *
     * @param menuManager
     *            The menu manager with which to compare; may be
     *            <code>null</code>.
     * @param selectionProvider
     *            The selection provider with which to compare; may be
     *            <code>null</code>.
     * @param part
     *            The part with which to compare; may be <code>null</code>.
     * @return <code>true</code> if the menu manager, selection provider and
     *         part are all the same.
     */
    public final boolean matches(final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final IWorkbenchPart part) {
        return (this.menu == menuManager)
                && (this.selProvider == selectionProvider)
                && (this.part == part);
    }

    /**
     * Contributes items registered for the currently active editor.
     */
